<template>
  <!-- contents -->
  <div class="contents-wrap">
    <!-- 공통영역 x -->
    <section class="contents">
      <div class="contents-info">
        <h3 class="contents-title">월별 ckd(캠스)</h3>
      </div>
      <div style="display:flex;flex-direction: row">
        <div style="width:50%;padding:3px;">
          <search
            :search-list-data="Raw.searchListData"
            :list-data="Raw.searchListData"
            @searchresult="searchResultRaw"
            :searchAry="Raw.searchAry"
          />
          <gridmain
            :list-data="Raw.listData"
            :list-meta="Raw.listMeta"
            :total-count="Raw.totalCount"
            :header-buttons="Raw.headerButtons"
            :paging-yn="Raw.paginYn"
            :excel-meta="Raw.excelMeta"
            excel-name="월별Ckd.xls"
            gridId="grid1"
            grid-height="50vh"            
          />
        </div>
        <div style="width:50%;padding:3px">
          <search
            :search-list-data="Sum.searchListData"
            :list-data="Sum.searchListData"
            @searchresult="searchResultSum"
            :searchAry="Sum.searchAry"
          />
          <gridmain
            :list-data="Sum.listData"
            :list-meta="Sum.listMeta"
            :header-buttons="Sum.headerButtons"
            :paging-yn="Sum.paginYn"
            :totalCount="Sum.totalCount"
            gridId="grid2"
            grid-height="50vh"            
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
  name: "ckd_mon",
  data() {
    return {
      Raw: {
        searchAry:['ITEM_NO', 'ITEM_NM'],
        listMeta: {          
          meta: [
            { col: "ITEM_NO", name: "품번", size: "180px" },
            { col: "ITEM_NM", name: "품명", size: "300px" },
            { col: "MON", name: "월", size: "100px" },
            { col: "CNT", name: "수량" },
          ],
        },
        listData: [],
        excelMeta: {
          품번: "ITEM_NO",
          품명: "ITEM_NM",
          월: "MON",
          수량: "CNT",        
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
            text: "조회",
          },
          {
            type: "Import",
            callback: function(vm, file) {
              vm.$options.parent.upload(file);
            },
            text: "Excel 업로드",
          },
          {
            type: "normal",
            callback: function(vm) {
              vm.$options.parent.insertSum();
            },
            text: "집계",
          },
        ],
        totalCount: 0,
        paginYn: "N",
      },
      Sum: {
        searchAry:['ITEM_NO', 'ITEM_NM'],
        listMeta: {
          meta: [
            { col: "ITEM_NO", name: "품번", size: "180px" },
            { col: "ITEM_NM", name: "품명", size: "300px" },
            { col: "MON", name: "월", size: "100px" },
            { col: "SUM_CNT", name: "수량" },
          ],
        },
        listData: [],
        searchListData: [],
        headerButtons: [
          {
            type: "normal",
            callback: function(vm) {
              vm.$options.parent.getSumList();
            },
            text: "조회",
          },
        ],
        totalCount: 0,
        paginYn: "N",
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
    searchResultRaw(searchData) {
      if (searchData) {
        //this.Raw.listData.splice(0, this.Raw.listData.length);
        this.Raw.listData = searchData;
      } else {
        this.search();
      }
    },
    searchResultSum(searchData) {
      if (searchData) {
        //this.Raw.listData.splice(0, this.Raw.listData.length);
        this.Sum.listData = searchData;
      } else {
        this.getSumList();
      }
    },
    getSearch() {
      this.search();
    },
    async search() {
      //params":{"query":{"PlayerTypeName":"video"}}

      await this.getRawList();
    },
    async getRawList() {
      let me = this;
      let param = {
        mon: this.getMon(),
      };
      carApi
        .getItemMonCkdList(param)
        .then((result) => {
          console.log("getItemMonCkdList:", result);
          if (result.data.retCode === "0") {
            let data = result.data.data;
            me.Raw.totalCount = data.length;

            me.Raw.listData.splice(0, me.Raw.listData.length);
            me.Raw.searchListData.splice(0, me.Raw.searchListData.length);
            me.Raw.listData = data;
            me.Raw.searchListData = data;
          } else {
            console.log(result.data.errMsg);
          }
        })
        .catch((error) => {
          console.error("getItemList:", error);
        });
    },
    async getSumList() {
      let me = this;
      let param = {
        mon: this.getMon(),
      };
      carApi
        .getItemCkdSumList(param)
        .then((result) => {
          console.log("getItemRcarSumList:", result);
          if (result.data.retCode === "0") {
            let data = result.data.data;
            me.Sum.totalCount = data.length;

            me.Sum.listData.splice(0, me.Sum.listData.length);
            me.Sum.searchListData.splice(0, me.Sum.searchListData.length);
            me.Sum.listData = data;
            me.Sum.searchListData = data;
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
        .itemMonCkdUpload(frm)
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
    insertSum() {
      let me = this;
      let param = {
        mon: this.getMon(),
      };
      carApi
        .insertItemCkdSum(param)
        .then((result) => {
          console.log("insertItemRcarSum:", result);
          if (result.data.retCode === "0") {
            me.getSumList();
          } else {
            console.log(result.data.errMsg);
          }
        })
        .catch((error) => {
          alert("오류발생");
          console.error("insertItemRcarSum:", error);
        });
    },
  },
};
</script>
