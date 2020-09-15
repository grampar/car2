<template>
  <!-- contents -->
  <div class="contents-wrap">
    <!-- 공통영역 x -->
    <section class="contents">
      <div class="contents-info">
        <h3 class="contents-title">rcar</h3>
      </div>
      <search
        :search-list-data="searchListData"
        :list-data="searchListData"
        @searchresult="searchResult"
        searchcolumn="ITEM_NO"
      />
      <gridmain
        :list-data="listData"
        :list-meta="listMeta"
        :total-count="totalCount"
        :header-buttons="headerButtons"
        :paging-yn="paginYn"
        gridId="grid1"
        grid-height="55vh"
      />
    </section>
    <!-- //공통영역 x -->
  </div>
  <!-- //contents -->
</template>

<script>
import carApi from "@/api/car";
export default {
  name: "rcar",
  data() {
    return {
      listMeta: {
        dblclickCallback: function (vm, data) {
          vm.$options.parent.showLayerPopup(data);
        },
        meta: [
          {
            col: "input=checkbox",
            name: "",
            size: "50px",
            targetid: "chkgrid",
          },
          { col: "ITEM_NO", name: "품번", size: "300px" },
          { col: "ITEM_NM", name: "품명", size: "500px" },
          { col: "MON", name: "월", size: "100px" },
          { col: "CNT", name: "수량", size: "100px" },
          { col: "REG_DT", name: "입력일시" },
        ],
      },
      listData: [],
      searchListData: [],
      headerButtons: [
        {
          type: "normal",
          callback: function (vm) {
            vm.$options.parent.getSearch();
          },
          text: "Refresh",
        },
        {
          type: "date",
        },
        {
          type: "Import",
          callback: function (vm, file) {
            vm.$options.parent.upload(file);
          },
          text: "Excel upload",
        },
      ],
      totalCount: 0,
      paginYn: "N",
      mode: "",
      selectedData: {
        itemNo: "",
        itemNm: "",
        itemKind: "",
        oemPrice: 0,
        asPrice: 0,
      },
    };
  },
  created() {},
  mounted() {
    this.getSearch();
    //setTimeout(this.getSearch, 500);
  },
  updated() {},
  methods: {
    searchResult(searchData) {
      if (searchData) {
        this.listData.splice(0, this.listData.length);
        this.listData = searchData;
      } else {
        this.search();
      }
    },
    getSearch() {
      this.search();
    },
    async search() {
      //params":{"query":{"PlayerTypeName":"video"}}

      await this.getItemList();
    },
    async getItemList() {
      let me = this;
      let param = {
        mon: this.getMon(),
      };
      carApi
        .getItemMonRcarList(param)
        .then((result) => {
          console.log("getItemMonRcarList:", result);
          if (result.data.retCode === "0") {
            let data = result.data.data;
            me.totalCount = data.length;

            me.listData.splice(0, me.listData.length);
            me.searchListData.splice(0, me.searchListData.length);
            me.listData = data;
            me.searchListData = data;
          } else {
            console.log(result.data.errMsg);
          }
        })
        .catch((error) => {
          console.error("getItemList:", error);
        });
    },
    getMon() {
      let year = document.querySelector("#damYear").value;
      let mon = document.querySelector("#damMon").value;
      return year + mon;
    },
    upload(file) {
      var frm = new FormData();
      frm.append("file", file);
      frm.append("mon", this.getMon());
      let me = this;

      carApi
        .itemMonRcarUpload(frm)
        .then((result) => {
          console.log(result);
          me.search();
        })
        .catch((error) => {
          alert("오류발생 관리자에게 문의");
          console.error(error);
        });
    },
  },
};
</script>
