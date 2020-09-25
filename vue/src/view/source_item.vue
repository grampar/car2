<template>
  <!-- contents -->
  <div class="contents-wrap">
    <!-- 공통영역 x -->
    <section class="contents">
      <div class="contents-info">
        <h3 class="contents-title">원재료 관리</h3>
         <search
          :search-list-data="Item.searchListData"
          :list-data="Item.searchListData"
          @searchresult="searchResultItem"
          :searchAry="Item.searchAry"
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
            :list-data="Src.listData"
            :list-meta="Src.listMeta"
            :header-buttons="Src.headerButtons"
            :paging-yn="Src.paginYn"
            :totalCount="Src.totalCount"
            gridId="grid1"
            grid-height="50vh"
            gridname="원재료"
          />
        </div>
      </div>
    </section>
    <!-- //공통영역 x -->

  </div>
  <!-- //contents -->
</template>

<script>
import srcApi from "@/api/src";
export default {
  name: "source_item",
  data() {
    return {
      Item: {
        searchAry:['ITEM_NO', 'ITEM_NM'],
        listMeta: {
          clickcallback: function(vm, data) {            
            vm.$options.parent.getSrcItemList(data.ITEM_NO);
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
      Src: {
        listMeta: {          
          meta: [
            {
              col: "input=checkbox",
              name: "checked",
              size: "100px",
              targetid: "chkgrid",
            },            
            { col: "SRC_NO", name: "원재료번호", size:"200px"},
            { col: "SRC_NM", name: "원재료명", size:"200px"},
            { col: "input=text", col2:"SRC_RATE", name: "비율"},

          ],
        },
        listData: [],
        searchListData: [],
        headerButtons: [          
          {
            type: "checkbox",
            callback: function(param, vm) {                            
              vm.$options.parent.insertSrcItem(param);
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
      srcApi
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
    getSrcItemList(itemNo) {
      let me = this;
      me.Item.itemNo = itemNo;
      let param = {itemNo:itemNo};
      srcApi
        .getSrcItemList(param)
        .then((result) => {
          if (result.data.retCode === "0") {
            let data = result.data.data;
            me.Src.totalCount = data.length;
            me.Src.listData.splice(0, me.Src.listData.length);
            me.Src.searchListData.splice(0, me.Src.searchListData.length);
            me.Src.listData = data;
            me.Src.searchListData = data;
          } else {
            console.log(result.data.errMsg);
          }
        })
        .catch((error) => {
          alert("오류발생 관리자에게 문의");
          console.error(error);
        });
    },
    insertSrcItem(param) {
      let data = { itemNo: this.Item.itemNo, list: param };
      let itemNo = this.Item.itemNo;
      srcApi
        .insertSrcItem(data)
        .then((result) => {
          console.log("insertSrcItem:", result);
          if (result.data.retCode === "0") {
            this.getSrcItemList(itemNo);
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
