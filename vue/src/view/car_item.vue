<template>
  <!-- contents -->
  <div>
    <!-- 공통영역 x -->
    <section class="contents">
      <div class="contents-info">
        <h3 class="contents-title">자동차 부속연결</h3>        
        <span></span>
      </div>
      <div style="display:flex;flex-direction: row">
        <div style="width:50%;padding:3px;">
          <search
            :search-list-data="Car.searchListData"
            :list-data="Car.searchListData"
            @searchresult="searchResultCar"
            searchcolumn="CAR_CODE"
          />
          <gridmain
            :list-data="Car.listData"
            :list-meta="Car.listMeta"
            :header-buttons="Car.headerButtons"
            :paging-yn="Car.paginYn"
            :totalCount="Car.totalCount"
            gridId="grid1"
            grid-height="57vh"
          />
        </div>
        <div style="width:50%;padding:3px">
          <search
            :search-list-data="Item.searchListData"
            :list-data="Item.searchListData"
            @searchresult="searchResultItem"
            searchcolumn="ITEM_NO"
          />
          <gridmain
            :list-data="Item.listData"
            :list-meta="Item.listMeta"
            :header-buttons="Item.headerButtons"
            :paging-yn="Item.paginYn"
            :totalCount="Item.totalCount"
            gridId="grid2"
            grid-height="57vh"
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
          clickcallback: function (vm, data) {
            vm.$options.parent.getItemList(data.CAR_CODE);
          },
          dblclickCallback: function (vm, data) {
            vm.$options.parent.showLayerPopup(data);
          },
          meta: [{ col: "CAR_CODE", name: "Code", size: "150px" }],
        },
        listData: [],
        searchListData: [],
        headerButtons: [
          {
            type: "normal",
            callback: function (vm) {
              vm.$options.parent.search();
            },
            text: "Refresh",
          },                    
          
        ],
        paginYn: "N",
        searchData: "",
        totalCount:0,
      },
      Item: {
        listMeta: {
          clickcallback: function (vm, data) {
            vm.$options.parent.getCarMonCntList(data.CAR_CODE);
          },
          dblclickCallback: function (vm, data) {
            vm.$options.parent.showLayerPopup(data);
          },
          meta: [
            {
              col: "input=checkbox",
              name: "checked",
              size: "100px",
              targetid: "chkgrid",
            },
            { col: "ITEM_NO", name: "품번" },
            { col: "ITEM_NM", name: "품명" },            
            { col: "OEM_PRICE", name: "Oem단가" },            
            { col: "AS_PRICE", name: "As단가" },            
          ],
        },
        listData: [],
        searchListData: [],                
        headerButtons: [
          {
            type: "checkbox",
            callback: function (vm, param) {
              console.log(param);
              vm.$options.parent.showLayerPopup();
            },
            text: "Save",
          },
        ],
        paginYn: "N",
        totalCount:0,
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
    searchResultCar(searchData) {
      if (searchData) {
        this.Car.listData.splice(0, this.Car.listData.length);
        this.Car.listData = searchData;
      } else {
        this.carSearch();
      }
    },
    searchResultItem(searchData) {
      if (searchData) {
        this.Item.listData.splice(0, this.Item.listData.length);
        this.Item.listData = searchData;
      } else {
        this.getItemList();
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
            me.Car.totalCount=data.length;
            me.Car.listData.splice(0, me.Car.listData.length);
            me.Car.searchListData.splice(0, me.Car.searchListData.length);
            me.Car.listData = data;
            me.Car.searchListData = data;
          } else {
            console.log(result.data.errMsg);
          }
        })
        .catch((error) => {
          alert("오류발생 관리자에게 문의")
          console.error(error);
        });
    },
    getItemList(carCode) {
      let me = this;
      let param = {        
        carCode:carCode
      };
      carApi
        .getCarItemList(param)
        .then((result) => {         
          console.log("getCarItemList:", result) 
          if (result.data.retCode === "0") {
            let data = result.data.data;
            me.Item.totalCount=data.length;
            me.Item.listData.splice(0, me.Item.listData.length);
            me.Item.searchListData.splice(0, me.Item.searchListData.length);
            me.Item.listData = data;
            me.Item.searchListData = data;
          } else {
            console.log(result.data.errMsg);
          }
        })
        .catch((error) => {
          alert("오류발생 관리자에게 문의")
          console.error(error);
        });
    },        
  },
};
</script>
