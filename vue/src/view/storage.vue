<template>
  <!-- contents -->
  <div>
    <!-- 공통영역 x -->
    <section class="contents">
      <div class="contents-info">
        <h3 class="contents-title">Storage</h3>
        <p class="contents-path">Home &gt; CA &gt; Storage</p>
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

    <!-- layer popup영역 -->
    <div
      id="layerPopup"
      class="layer-wrap"
      data-layer="modal-find-id"
      tabindex="0"
      style="display: none; z-index: 22; opacity: 0;"
    >
      <div class="layer-container">
        <section class="ui-layer ui-layer--large">
          <div class="ui-layer__head">
            <h2 class="ui-layer__title">Storage 추가</h2>
          </div>
          <div class="ui-layer__body" tabindex="0">
            <div class="ui-layer__body-inner mCustomScrollbar _mCS_1 mCS_no_scrollbar">
              <div
                id="mCSB_1"
                class="mCustomScrollBox mCS-light mCSB_vertical mCSB_inside"
                style="max-height: none;"
                tabindex="0"
              >
                <div
                  id="mCSB_1_container"
                  class="mCSB_container mCS_y_hidden mCS_no_scrollbar_y"
                  style="position:relative; top:0; left:0;"
                  dir="ltr"
                >
                  <div class="table-write-info">
                    <p class="table-write-info-required">
                      <span class="table-required"></span>필수입력항목
                    </p>
                  </div>
                  <div class="table-write">
                    <table>
                      <colgroup>
                        <col width="200px" />
                        <col />
                      </colgroup>
                      <tbody>
                        <tr>
                          <th>
                            Name
                            <span class="table-required">필수항목</span>
                          </th>
                          <td>
                            <div class="item-input">
                              <input type="text" id="Name" v-model="selectedData.Name" />
                            </div>
                          </td>
                          <th>
                            Label
                            <span class="table-required">필수항목</span>
                          </th>
                          <td>
                            <div class="item-input">
                              <input type="text" v-model="selectedData.Label" />
                            </div>
                          </td>
                        </tr>
                        <tr>
                          <th>Description</th>
                          <td colspan="3">
                            <div class="item-input">
                              <input type="text" v-model="selectedData.Description" />
                            </div>
                          </td>
                        </tr>
                        <tr>
                          <th>STORAGE PATH</th>
                          <td colspan="3">
                            <div class="item-input">
                              <input type="text" v-model="selectedData.StoragePath" />
                            </div>
                          </td>
                        </tr>
                        <tr>
                          <th>STORAGE NUM</th>
                          <td>
                            <div class="item-input">
                              <input type="text" v-model="selectedData.StorageNumber" disabled />
                            </div>
                          </td>
                          <th>LOWER LIMIT</th>
                          <td>
                            <div class="item-input">
                              <input type="text" v-model="selectedData.LowerLimit" />
                            </div>
                          </td>
                        </tr>
                      </tbody>
                    </table>
                  </div>
                </div>
                <div
                  id="mCSB_1_scrollbar_vertical"
                  class="mCSB_scrollTools mCSB_1_scrollbar mCS-light mCSB_scrollTools_vertical"
                  style="display: none;"
                >
                  <div class="mCSB_draggerContainer">
                    <div
                      id="mCSB_1_dragger_vertical"
                      class="mCSB_dragger"
                      style="position: absolute; min-height: 30px; top: 0px; height: 0px;"
                    >
                      <div class="mCSB_dragger_bar" style="line-height: 30px;"></div>
                    </div>
                    <div class="mCSB_draggerRail"></div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="ui-layer__foot">
            <div class="btns-box">
              <button
                type="button"
                class="btn btn-c-secondary"
                data-role="layerClose"
                @click="showLayerPopup"
              >취소</button>
              <button type="button" class="btn btn-c-primary" @click="addMediaServer">확인</button>
            </div>
          </div>
          <button
            type="button"
            class="ui-button ui-layer__close"
            data-role="layerClose"
            @click="showLayerPopup"
          >
            <span class="hidden">모달 닫기</span>
          </button>
        </section>
      </div>
    </div>
    <!-- layer popup영역 끝-->
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
          { col: "Name", name: "NAME", size: "150px" },
          { col: "StorageNumber", name: "STORAGE NUMBER", size: "100px" },
          { col: "StorageType", name: "Storage Type", size: "100px" },
          { col: "StoragePath", name: "Storage Path", size: "200px" },
          { col: "record", name: "Related Rcs", size: "" },
        ],
      },
      listData: [],
      searchListData: [],
      storageList: [],
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
          text: "Add",
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
    addMediaServer() {
      if (!this.selectedData.Name) {
        this.AlertMessage = "Name값을 입력해주세요.";
        this.isAlertShow = "visible";
        return;
      }
      if (!this.selectedData.Label) {
        this.AlertMessage = "Label값을 입력해주세요.";
        this.isAlertShow = "visible";
        return;
      }

      if (!this.selectedData.StoragePath) {
        this.AlertMessage = "StoragePath값을 입력해주세요.";
        this.isAlertShow = "visible";
        return;
      }

      let param, method;
      if (this.mode === "update") {
        method = "object.upsert";
        param = this.updateData;
        param.Label = this.selectedData.Label;
        param.Description = this.selectedData.Description;
        param.Properties.StoragePath[0] = this.selectedData.StoragePath;
        param.Properties.StorageNumber[0] = this.selectedData.StorageNumber;
        param.Properties.StorageType[0] = this.selectedData.StorageType;
        param.Properties.LowerLimit[0] = this.selectedData.LowerLimit;
      } else {
        method = "object.save";
        param = {
          Name: this.selectedData.Name,
          Label: this.selectedData.Label,
          Description: this.selectedData.Description,
          Relations: { recordserver: [] },
          Properties: {
            ErasingMethod: ["0"],
            LowerLimit: [this.selectedData.LowerLimit],
            StorageName: [this.selectedData.StoragePath],
            StorageNumber: [this.selectedData.StorageNumber],
            StoragePath: [this.selectedData.StoragePath],
            StorageType: [this.selectedData.StorageType],
            map: ["{}"],
          },
          ObjectTypeName: "recordserver",
          PlayerTypeName: "recordserver",
          PlayerProperties: null,
          RelationSource: {
            From: [],
            Pair: [],
            To: [],
          },
        };
      }

      let data = [];
      data.push(param);

      this.$ds
        .get(method, { data: data })
        .then((retVal) => {
          console.log(retVal);
          this.showLayerPopup();
          this.getSearch();
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
    cameraChange(value) {
      let ary = value.split(",");
      this.selectVideoList = ary;
    },
    storageChange(value) {
      let ary = value.split(",");
      this.selectStorage = ary;
    },
  },
};
</script>
