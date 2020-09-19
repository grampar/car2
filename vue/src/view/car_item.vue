<template>
  <!-- contents -->
  <div>
    <!-- 공통영역 x -->
    <section class="contents">
      <div class="contents-info">
        <h3 class="contents-title">자동차 Item관리</h3>
        <search
          :search-list-data="Item.searchListData"
          :list-data="Item.searchListData"
          @searchresult="searchResultItem"
          searchcolumn="ITEM_NO"
        />
      </div>
      <div style="display:flex;flex-direction: row">
        <div style="width:50%;padding:3px;">
          <gridmain
            :list-data="Item.listData"
            :list-meta="Item.listMeta"
            :header-buttons="Item.headerButtons"
            :paging-yn="Item.paginYn"
            :totalCount="Item.totalCount"
            gridId="grid2"
            grid-height="50vh"
            gridname="아이템"
          />          
        </div>
        <div style="width:50%;padding:3px">
          <gridmain
            :list-data="Car.listData"
            :list-meta="Car.listMeta"
            :header-buttons="Car.headerButtons"
            :paging-yn="Car.paginYn"
            :totalCount="Car.totalCount"
            gridId="grid1"
            grid-height="50vh"
            gridname="자동차"
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
      Item: {
        listMeta: {
          clickcallback: function(vm, data) {            
            vm.$options.parent.getCarItemList(data.ITEM_NO);
          },          
          meta: [            
            { col: "ITEM_NO", name: "품번", size: "150px" },
            { col: "ITEM_NM", name: "품명" },            
          ],
        },
        listData: [],
        searchListData: [],
        headerButtons: [
          {
            type: "normal",
            callback: function(vm) {
              vm.$options.parent.search();
            },
            text: "조회",
          },          
        ],
        paginYn: "N",
        totalCount: 0,
        itemNo: "",
      },
      Car: {
        listMeta: {          
          meta: [
            {
              col: "input=checkbox",
              name: "checked",
              size: "100px",
              targetid: "chkgrid",
            },
            { col: "CAR_CODE", name: "자동차코드", size: "150px" },
            { col: "CAR_NM", name: "자동차명"},
            ],
        },
        listData: [],
        searchListData: [],
        headerButtons: [          
          {
            type: "checkbox",
            callback: function(param, vm) {                            
              vm.$options.parent.insertCatItem(param);
            },
            text: "Save",
          },
        ],
        paginYn: "N",
        searchData: "",
        totalCount: 0,        
      },
    }
  },
  created() {
    this.search();
    //setTimeout(this.getSearch, 500);
  },
  mounted() {},
  updated() {},
  methods: {
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
      await this.getItemList();
    },

    
    getItemList() {
      let me = this;      
      let param = {      
      };
      carApi
        .getItemList(param)
        .then((result) => {
          console.log("getItemList:", result);
          if (result.data.retCode === "0") {
            let data = result.data.data;
            me.Item.totalCount = data.length;
            me.Item.listData.splice(0, me.Item.listData.length);
            me.Item.searchListData.splice(0, me.Item.searchListData.length);
            me.Item.listData = data;
            me.Item.searchListData = data;
          } else {
            console.log(result.data.errMsg);
          }
        })
        .catch((error) => {
          alert("오류발생 관리자에게 문의");
          console.error(error);
        });
    },
    getCarItemList(itemNo) {
      let me = this;
      me.Item.itemNo = itemNo;
      let param = {itemNo:itemNo};
      carApi
        .getCarItemList(param)
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
    insertCatItem(param) {
      let data = { itemNo: this.Item.itemNo, list: param };
      let itemNo = this.Item.itemNo;
      carApi
        .insertCarItem(data)
        .then((result) => {
          console.log("insertCatItem:", result);
          if (result.data.retCode === "0") {
            this.getCarItemList(itemNo);
          } else {
            console.log(result.data.errMsg);
          }
        })
        .catch((error) => {
          alert("오류발생 관리자에게 문의");
          console.error(error);
        });
    },
  },
};
</script>
