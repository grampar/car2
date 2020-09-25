<template>
  <!-- contents -->
  <div class="contents-wrap">
    <!-- 공통영역 x -->
    <section class="contents">
      <div class="contents-info">
        <h3 class="contents-title">원재료 관리</h3>
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
        gridId="grid1"
        grid-height="52vh"
      />
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
        <section class="ui-layer">
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
                      </colgroup>
                      <tbody>    
                         <tr>
                          <th>
                            원재료번호
                            <span class="table-required">필수항목</span>
                          </th>
                          <td>
                            <div class="item-input">
                              <input
                                type="text"
                                id="srcNo"
                                v-model="selectedData.srcNo"
                              />
                            </div>
                          </td>
                        </tr>                    
                        <tr>
                          <th>
                            원재료명
                            <span class="table-required">필수항목</span>
                          </th>
                          <td>
                            <div class="item-input">
                              <input
                                type="text"
                                v-model="selectedData.srcNm"
                              />
                            </div>
                          </td>
                        </tr>
                        <tr>
                          <th>
                            원재료 단가
                            <span class="table-required">필수항목</span>
                          </th>
                          <td>
                            <div class="item-input">
                              <input
                                type="text"
                                id="itemNo"
                                v-model="selectedData.srcPrice"
                              />
                            </div>
                          </td>                          
                        </tr>
                        <tr>
                          <th>
                            원재료 단위
                            <span class="table-required">필수항목</span>
                          </th>
                          <td>
                            <div class="item-input">
                              <input
                                type="text"
                                id="itemNo"
                                v-model="selectedData.srcUnit"
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
import srcApi from "@/api/src";
export default {
  name: "source",
  data() {
    return {
      searchAry:['SRC_NO', 'SRC_NM'],
      listMeta: {
        dblclickCallback: function(vm, data) {
          vm.$options.parent.showLayerPopup(data);
        },
        meta: [
          {
            col: "input=checkbox",
            name: "",
            size: "50px",
            targetid: "chkgrid",
          },          
          { col: "SRC_NO", name: "원재료번호", size: "200px" },
          { col: "SRC_NM", name: "원재료명", size: "200px" },
          { col: "SRC_PRICE", name: "원재료단가", size: "100px" },          
          { col: "SRC_UNIT", name: "SRC 단위"},
        ],
      },
      listData: [],
      searchListData: [],
      headerButtons: [
        {
          type: "normal",
          callback: function(vm) {
            vm.$options.parent.getSearch();
          },
          text: "Refresh",
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
      totalCount: 0,
      paginYn: "N",
      mode: "",
      selectedData: {
        srcNo: "",                
        srcNm: "",                
        srcPrice: 0,
        srcUit: "",
      },
    };
  },
  created() {
    this.getSearch();
    //setTimeout(this.getSearch, 500);
  },
  mounted() {},
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

      await this.getSrcList();
    },
    async getSrcList() {
      let me = this;
      srcApi
        .getSrcList({})
        .then((result) => {
          console.log("getItemList:", result);
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
    showLayerPopup() {
      let layer = document.querySelector("#layerPopup");
      if (layer.style.display == "none") {
        let data;
        if (arguments[0]) {
          data = arguments[0];
          document.querySelector("#srcNo").setAttribute("disabled", true);
          this.selectedData.srcNo = data.SRC_NO;          
          this.selectedData.srcNm = data.SRC_NM;          
          this.selectedData.srcPrice = data.SRC_PRICE;
          this.selectedData.srcUnit = data.SRC_UNIT;

          this.mode = "update";
        } else {
          this.mode = "insert";
        }

        layer.style.display = "block";
        layer.style.opacity = "100";
      } else {
        document.querySelector("#srcNo").removeAttribute("disabled");
        this.selectedData.srcNo = "";          
        this.selectedData.srcNm = "";          
        this.selectedData.srcPrice = "";
        this.selectedData.srcUnit = "";


        layer.style.display = "none";
        layer.style.opacity = "0";
      }
    },
    addData() {
      if (!this.selectedData.srcNo) {
        alert("원재료번호를 입력해주세요");
        return;
      }
      if (!this.selectedData.srcNm) {
        alert("원재료명을 입력해주세요");
        return;
      }
      if (!this.selectedData.srcPrice) {
        alert("원재료가격을 입력해주세요");
        return;
      }
      if (!this.selectedData.srcUnit) {
        alert("원재료단위를 입력해주세요");
        return;
      }
     

      let me = this;
      srcApi
        .insertSrc(this.selectedData)
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
    upload(file) {
      var frm = new FormData();
      frm.append("file", file);
      let me = this;

      srcApi
        .srcFileupload(frm)
        .then((result) => {
          console.log(result);
          alert("저장이 완료");
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
