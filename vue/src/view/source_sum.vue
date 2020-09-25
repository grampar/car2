<template>
  <!-- contents -->
  <div class="contents-wrap">
    <!-- 공통영역 x -->
    <section class="contents">
      <div class="contents-info">
        <h3 class="contents-title">매입현황</h3>
      </div>
      <search
          :search-list-data="searchListData"
          :list-data="searchListData"
          @searchresult="searchResult"          
          :searchAry="searchAry"
        />
      <gridmain
        :list-data="listData"
        :list-meta="listMeta"
        :total-count="totalCount"
        :header-buttons="headerButtons"
        :paging-yn="paginYn"
        :excel-meta="excelMeta"
        excel-name="매입현황.xls"
        gridId="grid1"
        grid-height="50vh"
      />
    </section>
    <!-- //공통영역 x -->
  </div>
  <!-- //contents -->
</template>

<script>
import srcApi from "@/api/src";
export default {
  name: "source_sum",
  data() {
    return {
      searchAry:['SRC_NO', 'ITEM_NO'],
      listMeta: {
        dblclickCallback: function(vm, data) {
          vm.$options.parent.showLayerPopup(data);
        },
        meta: [          
          { col: "SRC_NO", name: "GRADE", size: "160px" },
          { col: "ITEM_NO", name: "품번", size: "150px" },
          { col: "ITEM_NM", name: "품명", size: "100px", limit: 8 },
          { col: "CAR_CNT", name: "실적", size: "80px" },
          { col: "SRC_UNIT", name: "단위", size: "80px" },
          { col: "SRC_RATE", name: "원수", size: "80px" },
          { col: "SRC_PRICE", name: "단가", size: "100px" },
          { col: "STANDARD_PURCHASE", name: "매입표준", size: "110px" },
          { col: "DEADLINE_PURCHASE", name: "매입마감", size: "110px" },
          { col: "PURCHASE_MINUS", name: "매입차이", size: "140px" },
          { col: "STANDARD_PRICE", name: "금액표준", size: "140px" },
          { col: "DEADLINE_PRICE", name: "금액마감", size: "140px" },
          { col: "PRICE_MINUS", name: "금액차이"},          
        ],
      },
      listData: [],
      excelMeta: {
        GRADE: "SRC_NM",
        품명: "ITEM_NO",
        품번: "ITEM_NM",
        실적: "SRC_UNIT",
        단위: "CAR_CNT",
        원수: "SRC_RATE",
        "단가": "SRC_PRICE",
        표준: "STANDARD_PURCHASE",
        "마감": "DEADLINE_PURCHASE",
        차이: "PURCHASE_MINUS",
        금액표준: "STANDARD_PRICE",
        금액마감: "DEADLINE_PRICE",
        금액차이: "PRICE_MINUS",        
      },
      searchListData: [],
      headerButtons: [
        {
          type: "date",
        },
        {
          type: "normal",
          callback: function(vm) {
            vm.$options.parent.getSearch();
          },
          text: "Refresh",
        },
        {
          type: "normal",
          callback: function(vm) {
            vm.$options.parent.insertSrcSum();
          },
          text: "집계",
        },
        {
            type: "Import",
            callback: function(vm, file) {
              vm.$options.parent.upload(file);
            },
            text: "Excel 업로드",
          },
      ],
      totalCount: 0,
      paginYn: "N",
    };
  },
  created() {
    //setTimeout(this.getSearch, 500);
  },
  mounted() {
    this.getSearch();
  },
  updated() {},
  methods: {
    searchResult(searchData) {
      if (searchData) {
        //this.listData.splice(0, this.listData.length);
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

      await this.getSrcSumList();
    },
    getMon() {
      let year = document.querySelector("#damYear").value;
      let mon = document.querySelector("#damMon").value;
      return year + mon;
    },
    async getSrcSumList() {
      let me = this;
      let param = {
        mon: this.getMon(),
      };
      srcApi
        .getSrcSumList(param)
        .then((result) => {
          console.log("getSrcSumList:", result);
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
          console.error("getSrcSumList:", error);
        });
    },
    insertSrcSum(param) {
      let data = { mon: this.getMon(), list: param };      
      srcApi
        .insertSrcSum(data)
        .then((result) => {
          console.log("insertSrcSum:", result);
          if (result.data.retCode === "0") {
            this.getSrcSumList();
          } else {
            console.log(result.data.errMsg);
          }
        })
        .catch((error) => {
          alert("오류발생 관리자에게 문의");
          console.error(error);
        });
    },
    upload(file) {
      var frm = new FormData();
      frm.append("file", file);
      frm.append("mon", this.getMon());
      let me = this;

      srcApi
        .updateSrcSum(frm)
        .then((result) => {
          console.log(result);
          alert("업로드 완료");
          me.getSrcSumList();
        })
        .catch((error) => {
          alert("오류발생 관리자에게 문의");
          console.error(error);
        });
    },
  },
};
</script>
