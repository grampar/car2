<template>
  <!-- contents -->
  <div>
    <!-- 공통영역 x -->
    <section class="contents">
      <div class="contents-info">
        <h3 class="contents-title">자동차</h3>
      </div>
      <div style="display:flex;flex-direction: row">
        <div style="width:30%;padding:3px;">
          <div class="table-search">
            <form name="searchForm" onsubmit="return false">
              <table>
                <colgroup>
                  <col width="170px" />
                  <col />
                </colgroup>
                <tbody>
                  <tr>
                    <th>검색</th>
                    <td>
                      <div class="item-input">
                        <input type="text" v-model="Car.searchData" @keyup.enter="getBtnSearch" />
                      </div>
                    </td>
                  </tr>
                </tbody>
              </table>
              <div class="table-search-btn">
                <button type="button" class="btn btn-c-tertiary" @click="getBtnSearch">Search</button>
              </div>
            </form>
          </div>
          <gridmain
            :list-data="Car.listData"
            :list-meta="Car.listMeta"
            :header-buttons="Car.headerButtons"
            :paging-yn="Car.paginYn"
            gridId="grid1"
            grid-height="60vh"
          />
        </div>
        <div style="width:70%;padding:3px">
          <gridmain
            :list-data="CarCnt.listData"
            :list-meta="CarCnt.listMeta"
            :header-buttons="CarCnt.headerButtons"
            :paging-yn="CarCnt.paginYn"
            gridId="grid2"
            grid-height="70vh"
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
            vm.$options.parent.showLayerPopup(data);
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
              vm.$options.parent.CustomMapSearch();
            },
            text: "Refresh",
          },
          {
            type: "Import",
            callback: function (vm, param) {
              console.log(param);
            },
            text: "Excel Import",
          },
        ],
        paginYn: "N",
        searchData: "",
      },
      CarCnt: {
        listMeta: {
          callback: function (vm, data) {
            vm.$options.parent.showLayerPopup(data);
          },
          meta: [
            { col: "", name: "NAME" },
            {
              col: "input=button",
              name: "visible",
              size: "100px",
              callback: function (vm, param) {
                console.log(vm, param);
                alert("aasd");
              },
            },
          ],
        },
        listData: [],
        searchListData: [],
        customTreeList: [],
        tmpList: [],
        headerButtons: [],
        paginYn: "N",
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
    getBtnSearch() {
      let searchTxt = this.Car.searchData.trim();
      if (searchTxt) {
        let newData = this.Car.searchListData.filter((ele) => {
          var re = new RegExp(searchTxt);
          return re.test(ele.carCode);
        });

        this.Car.listData.splice(0, this.Car.listData.length);
        this.Car.listData = newData;
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
          console.log(result);
          if (result.data.retCode === "0") {
            let data = result.data.data;
            me.Car.listData.splice(0, me.Car.listData.length);
            me.Car.searchListData.splice(0, me.Car.searchListData.length);
            me.Car.listData = data;
            me.Car.searchListData = data;
          } else {
            console.log(result.data.errMsg);
          }
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
};
</script>
