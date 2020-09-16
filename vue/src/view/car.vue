<template>
  <!-- contents -->
  <div>
    <!-- 공통영역 x -->
    <section class="contents">
      <div class="contents-info">
        <h3 class="contents-title">자동차</h3>
        <search
          :search-list-data="Car.searchListData"
          :list-data="Car.searchListData"
          @searchresult="searchResult"
          searchcolumn="CAR_CODE"
        />
      </div>
      <div style="display:flex;flex-direction: row">
        <div style="width:50%;padding:3px;">
          <gridmain
            :list-data="Car.listData"
            :list-meta="Car.listMeta"
            :header-buttons="Car.headerButtons"
            :paging-yn="Car.paginYn"
            :totalCount="Car.totalCount"
            gridId="grid1"
            grid-height="58vh"
          />
        </div>
        <div style="width:50%;padding:3px">
          <gridmain
            :list-data="CarCnt.listData"
            :list-meta="CarCnt.listMeta"
            :header-buttons="CarCnt.headerButtons"
            :paging-yn="CarCnt.paginYn"
            :totalCount="CarCnt.totalCount"
            gridId="grid2"
            grid-height="58vh"
          />
        </div>
      </div>
    </section>
    <!-- //공통영역 x -->
  </div>
  <!-- //contents -->
</template>

<script>
import carApi from "@/api/car";
export default {
  name: "car",
  data() {
    return {
      Car: {
        listMeta: {
          clickcallback: function(vm, data) {
            vm.$options.parent.getCarMonCntList(data.CAR_CODE);
          },
          dblclickCallback: function(vm, data) {
            vm.$options.parent.showLayerPopup(data);
          },
          meta: [{ col: "CAR_CODE", name: "Code", size: "150px" }],
        },
        listData: [],
        searchListData: [],
        headerButtons: [
          {
            type: "date",
          },
          {
            type: "normal",
            callback: function(vm) {
              vm.$options.parent.search();
            },
            text: "조회",
          },
          {
            type: "Import",
            callback: function(vm, file) {
              vm.$options.parent.upload(file);
            },
            text: "Excel 업로드",
          },
        ],
        paginYn: "N",
        searchData: "",
        totalCount: 0,
      },
      CarCnt: {
        listMeta: {
          callback: function(vm, data) {
            vm.$options.parent.showLayerPopup(data);
          },
          meta: [
            { col: "MON", name: "월" },
            { col: "CNT", name: "수량" },
          ],
        },
        listData: [],
        searchListData: [],
        headerButtons: [],
        paginYn: "N",
        totalCount: 0,
      },
      mode: "",
    };
  },
  created() {
    this.search();
    //setTimeout(this.getSearch, 500);
  },
  mounted() {},
  updated() {},
  methods: {
    searchResult(searchData) {
      if (searchData) {
        //this.Car.listData.splice(0, this.Car.listData.length);
        this.Car.listData = searchData;
      } else {
        this.search();
      }
    },
    async search() {
      //params":{"query":{"PlayerTypeName":"video"}}
      await this.carSearch();
    },

    async carSearch() {
      let me = this;
      let param = {};
      carApi
        .getCarList(param)
        .then((result) => {
          if (result.data.retCode === "0") {
            let data = result.data.data;
            me.Car.totalCount = data.length;
            me.Car.listData.splice(0, me.Car.listData.length);
            me.Car.searchListData.splice(0, me.Car.searchListData.length);
            me.Car.listData = data;
            me.Car.searchListData = data;
          } else {
            console.log(result.data.errMsg);
          }
        })
        .catch((error) => {
          alert("오류발생 관리자에게 문의");
          console.error(error);
        });
    },
    getCarMonCntList(carCode) {
      let me = this;
      let param = {
        mon: this.getMon(),
        carCode: carCode,
      };
      carApi
        .getCarMonCntList(param)
        .then((result) => {
          console.log("getCarMonCntList:", result);
          if (result.data.retCode === "0") {
            let data = result.data.data;
            me.CarCnt.totalCount = data.length;
            me.CarCnt.listData.splice(0, me.CarCnt.listData.length);
            me.CarCnt.searchListData.splice(0, me.CarCnt.searchListData.length);
            me.CarCnt.listData = data;
            me.CarCnt.searchListData = data;
          } else {
            console.log(result.data.errMsg);
          }
        })
        .catch((error) => {
          alert("오류발생 관리자에게 문의");
          console.error(error);
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
        .carFileupload(frm)
        .then((result) => {
          console.log(result);
          alert("업로드 완료");
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
