<template>
  <!-- contents -->
  <div class="contents-wrap">
    <!-- 공통영역 x -->
    <section class="contents">
      <div class="contents-info">
        <h3 class="contents-title">매출현황</h3>
      </div>
      <gridmain
        :list-data="listData"
        :list-meta="listMeta"
        :total-count="totalCount"
        :header-buttons="headerButtons"
        :paging-yn="paginYn"
        :excel-meta="excelMeta"
        excel-name="매출현황.xls"
        gridId="grid1"
        grid-height="65vh"
      />
    </section>
    <!-- //공통영역 x -->
  </div>
  <!-- //contents -->
</template>

<script>
import carApi from "@/api/car";
export default {
  name: "price_sum",
  data() {
    return {
      listMeta: {
        dblclickCallback: function(vm, data) {
          vm.$options.parent.showLayerPopup(data);
        },
        meta: [
          { col: "CAR_NM", name: "차종", size: "80px", limit:4 },
          { col: "ITEM_KIND", name: "구분", size: "100px" },
          { col: "ITEM_NO", name: "품번", size: "150px" },
          { col: "ITEM_NM", name: "품명", size: "120px", limit: 8 },
          { col: "OEM_PRICE", name: "oem단가", size: "100px" },
          { col: "AS_PRICE", name: "as단가", size: "100px" },
          { col: "CAR_CNT", name: "겜스/위주", size: "80px" },
          { col: "CKD_CNT", name: "CKD", size: "80px" },
          { col: "RCAR_CNT", name: "R-CAR", size: "80px" },
          { col: "MOBIS_CNT", name: "MOBIS", size: "100px" },
          { col: "TOTAL_CNT", name: "TTL", size: "80px" },
          { col: "AS_CNT", name: "AS", size: "80px" },
          { col: "OEM_TOTAL_PRICE", name: "OEM금액", size: "120px" },
          { col: "AS_TOTAL_PRICE", name: "AS금액", size: "120px" },
          { col: "TOTAL_PRICE", name: "합계" },
        ],
      },
      listData: [],
      excelMeta: {
        차종: "CAR_NM",
        구분: "ITEM_KIND",
        품번: "ITEM_NO",
        품명: "ITEM_NM",
        oem단가: "OEM_PRICE",
        as단가: "AS_PRICE",
        "겜스/위주": "CAR_CNT",
        CKD: "CKD_CNT",
        "R-CAR": "RCAR_CNT",
        MOBIS: "MOBIS_CNT",
        TTL: "TOTAL_CNT",
        AS: "AS_CNT",
        OEM금액: "OEM_TOTAL_PRICE",
        AS금액: "AS_TOTAL_PRICE",
        
        합계: "TOTAL_PRICE",
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

      await this.getItemList();
    },
    getMon() {
      let year = document.querySelector("#damYear").value;
      let mon = document.querySelector("#damMon").value;
      return year + mon;
    },
    async getItemList() {
      let me = this;
      let param = {
        mon: this.getMon(),
      };
      carApi
        .getPriceSum(param)
        .then((result) => {
          console.log("getPriceSum:", result);
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
          console.error("getPriceSum:", error);
        });
    },
  },
};
</script>
