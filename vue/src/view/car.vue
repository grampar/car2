<template>
  <!-- contents -->
  <div>
    <!-- 공통영역 x -->
    <section class="contents">
      <div class="contents-info">
        <h3 class="contents-title">자동차</h3>
        <search
          :search-list-data="searchListData"
          :list-data="searchListData"
          @searchresult="searchResult"
          searchcolumn="CAR_CODE"
        />
        <gridmain
            :list-data="listData"
            :list-meta="listMeta"
            :header-buttons="headerButtons"
            :paging-yn="paginYn"
            :totalCount="totalCount"
            gridId="grid1"
            grid-height="58vh"
          />        
      </div>      
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
            <h2 class="ui-layer__title">Item 추가</h2>
          </div>
          <div class="ui-layer__body" tabindex="0">
            <div
              class="ui-layer__body-inner mCustomScrollbar _mCS_1 mCS_no_scrollbar"
            >
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
                        <col width="200px" />
                        <col />
                      </colgroup>
                      <tbody>
                        <tr>
                          <th>
                            자동차코드
                            <span class="table-required">필수항목</span>
                          </th>
                          <td>
                            <div class="item-input">
                              <input
                                type="text"
                                id="carCode"
                                v-model="selectedData.carCode"
                              />
                            </div>
                          </td>
                          <th>
                            자동차명
                            <span class="table-required">필수항목</span>
                          </th>
                          <td>
                            <div class="item-input">
                              <input
                                type="text"
                                v-model="selectedData.carNm"
                              />
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
                      <div
                        class="mCSB_dragger_bar"
                        style="line-height: 30px;"
                      ></div>
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
              >
                취소
              </button>
              <button type="button" class="btn btn-c-primary" @click="addData">
                확인
              </button>
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
import carApi from "@/api/car";
export default {
  name: "car",
  data() {
    return {      
      listMeta: {
        dblclickCallback: function(vm, data) {
          vm.$options.parent.showLayerPopup(data);
        },          
        meta: [
          { col: "CAR_CODE", name: "자동차코드", size: "200px" },
          { col: "CAR_NM", name: "자동차명", size: "250px" },
          { col: "REG_DT", name: "등록일시" }
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
        {
          type: "normal",
          callback: function(vm, param) {
            console.log(param);
            vm.$options.parent.showLayerPopup();
          },
          text: "Add",
        },
      ],
      paginYn: "N",      
      totalCount: 0,
      mode: "",
      selectedData: {
        carCode: "",
        carNm: "",        
      },
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
          alert("오류발생 관리자에게 문의");
          console.error(error);
        });
    }, 
    showLayerPopup() {
      let layer = document.querySelector("#layerPopup");
      if (layer.style.display == "none") {
        let data;
        if (arguments[0]) {
          data = arguments[0];
          document.querySelector("#carCode").setAttribute("disabled", true);
          this.selectedData.carCode = data.CAR_CODE;
          this.selectedData.carNm = data.CAR_NM;          

          this.mode = "update";
        } else {
          this.mode = "insert";
        }

        layer.style.display = "block";
        layer.style.opacity = "100";
      } else {
        document.querySelector("#carCode").removeAttribute("disabled");
        this.selectedData.carCode = "";
        this.selectedData.carNm = "";

        layer.style.display = "none";
        layer.style.opacity = "0";
      }
    },
    addData() {
      if (!this.selectedData.carCode) {
        alert("자동차코드를 입력해주세요");
        return;
      }
      if (!this.selectedData.carNm) {
        alert("자동차명을 입력해주세요");
        return;
      }
     

      let me = this;
      carApi
        .insertCar(this.selectedData)
        .then((result) => {
          console.log("ItemInsert:", result);
          this.showLayerPopup();
          me.search();
        })
        .catch((error) => {
          alert("오류발생");
          console.error(error);
        });
    },
  },
};
</script>
