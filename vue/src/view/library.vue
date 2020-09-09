<template>
  <!-- contents -->
  <div>
    <!-- 공통영역 x -->
    <section class="contents">
      <div class="contents-info">
        <h3 class="contents-title">Storage</h3>
        <p class="contents-path">Home &gt; Contents &gt; Library</p>
      </div>
      <div>
        <search
          :search-list-data="searchListData"
          :list-data="searchListData"
          @searchresult="searchResult"
          searchcolumn="Name"
        />

        <gridmain
          :list-data="listData"
          :list-meta="listMeta"
          :header-buttons="headerButtons"
          :paging-yn="paginYn"
          gridId="grid1"
          grid-height="60vh"
        />
      </div>

      <alert :is-show="isAlertShow" :message="AlertMessage" @alertclose="alertClose"></alert>
    </section>
    <!-- //공통영역 x -->
  </div>
  <!-- //contents -->
</template>

<script>
export default {
  name: "storage",
  data() {
    return {
      listMeta: {
        callback: function (vm, data) {
          vm.$options.parent.showLayerPopup(data);
        },
        meta: [
          {
            col: "input=checkbox",
            name: "",
            size: "100px",
            targetid: "chkgrid",
          },
          { col: "name", name: "Name", size: "150px" },
          { col: "type", name: "Type", size: "100px" },
          { col: "updatedTime", name: "Last Changed Time", size: "100px" },
          { col: "user", name: "Last Changed User", size: "200px" },
        ],
      },
      listData: [],
      searchListData: [],
      fileList: [],
      headerButtons: [
        {
          type: "normal",
          callback: function (vm, param) {
            console.log(param);
            vm.$options.parent.search();
          },
          text: "Refresh",
        },
        {
          type: "normal",
          callback: function (vm, param) {
            console.log(param);
            vm.$options.parent.showLayerPopup();
          },
          text: "upload",
        },
        {
          type: "checkbox",
          callback: function (param, vm) {
            let names = [];
            param.forEach((ele) => {
              names.push(ele.Name);
            });
            vm.$ds
              .get("object.remove", { Name: names })
              .then((data) => {
                console.log(data);
                vm.$options.parent.getSearch();
              })
              .catch((e) => {
                console.log(e);
              });
          },
          text: "Remove",
        },
        {
          type: "Import",
          callback: function (vm, param) {
            console.log(param);
            vm.$ds
              .get("object.upsert", { data: param })
              .then((data) => {
                console.log(data);
                vm.$options.parent.getSearch();
              })
              .catch((e) => {
                console.log(e);
              });
          },
          text: "Import",
        },
        {
          type: "normal",
          callback: function (vm, param) {
            console.log(vm, param);
            vm.$options.parent.reqDataList(
              "object.list",
              { query: { PlayerTypeName: "storage" } },
              (mediaData) => {
                let content = JSON.stringify(mediaData);
                let a = document.createElement("a");
                let file = new Blob([content], { type: "application/json" });
                a.href = URL.createObjectURL(file);
                a.download = "storage.json";
                a.click();
              }
            );
          },
          text: "export",
        },
      ],
      totalCount: 0,
      recordCount: 10,
      pageCount: 10,
      curPage: 1,
      paginYn: "N",
      AlertMessage: "",
      isAlertShow: "hidden",
      mode: "",
      updateData: null,
      selectedData: {
        Name: "",
        Label: "",
        Description: "",
        StorageNumber: "3",
        StoragePath: "",
        StorageType: "1",
        LowerLimit: "",
      },
    };
  },
  created() {
    this.getSearch();
  },
  mounted() {},
  updated() {},
  methods: {
    searchResult(searchData) {
      if (searchData) {
        this.listData = searchData;
      } else {
        this.search();
      }
    },
    getSearch() {
      this.curPage = 1;
      this.search();
    },

    movePage(goPage) {
      this.curPage = goPage;
      this.search();
    },
    async search() {
      //params":{"query":{"PlayerTypeName":"video"}}
      let me = this;
      await this.reqDataList(
        "object.list",
        { query: { PlayerTypeName: "storage" } },
        (data) => {
          me.storageList.splice(0, this.storageList.length);

          if (data.totalCount) {
            me.totalCount = data.totalCount;
            me.storageList = data.list;
          } else {
            me.totalCount = data.length;
            me.storageList = data;
          }

          me.listData.splice(0, me.listData.length);
          me.searchListData.splice(0, me.searchListData.length);
          me.storageList.forEach((ele) => {
            let data = {};
            data._id = ele._id;
            data.Name = ele.Name;
            data.Label = ele.Label;
            data.StoragePath = ele.Properties.StoragePath[0];
            data.StorageNumber = ele.Properties.StorageNumber[0];
            data.StorageType = ele.Properties.StorageType[0];

            let recordString = "";
            ele.Relations.recordserver.forEach((record, index) => {
              if (index == 0) recordString += record;
              else recordString += "," + record;
            });
            data.record = recordString;

            me.listData.push(data);
            me.searchListData.push(data);
          });
        }
      );
    },

    showLayerPopup() {
      let layer = document.querySelector("#layerPopup");
      if (layer.style.display == "none") {
        let data;
        if (arguments[0]) {
          //data = this.getMediaData(arguments[0].Name);
          data = this.getListData(this.storageList, arguments[0].Name);
          document.querySelector("#Name").setAttribute("disabled", true);

          this.selectedData.Name = data.Name;
          this.selectedData.Label = data.Label;
          this.selectedData.Description = data.Description;
          this.selectedData.StoragePath = data.Properties.StoragePath[0];

          this.mode = "update";
          this.updateData = data;
        } else {
          this.mode = "insert";
        }

        layer.style.display = "block";
        layer.style.opacity = "100";
      } else {
        document.querySelector("#Name").removeAttribute("disabled", false);

        this.selectedData.Name = "";
        this.selectedData.Label = "";
        this.selectedData.Description = "";
        this.selectedData.StoragePath = "";

        layer.style.display = "none";
        layer.style.opacity = "0";
      }
    },

    async reqDataList(method, param, callback) {
      await this.$ds
        .get(method, param)
        //.get("object.list", { query: { PlayerTypeName: "mediaserver" } })
        .then((mediaData) => {
          callback(mediaData);
        })
        .catch((e) => {
          console.log(e);
        });
    },

    getListData(dataList, Name) {
      let cnt = dataList.length;
      let data;
      for (let i = 0; i < cnt; i++) {
        if (dataList[i].Name === Name) {
          data = dataList[i];
          break;
        }
      }
      return data;
    },
    alertClose(data) {
      this.isAlertShow = data;
    },
  },
};
</script>
