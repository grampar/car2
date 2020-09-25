<template>
  <!-- contents -->
  <div class="contents-wrap">
    <!-- 공통영역 x -->
    <section class="contents">
      <div class="contents-info">
        <h3 class="contents-title">아이템</h3>
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
        <section class="ui-layer ui-layer--large">
          <div class="ui-layer__head">
            <h2 class="ui-layer__title">Item 추가</h2>
          </div>
          <div class="ui-layer__body" tabindex="0">
            <div class="ui-layer__body-inner mCustomScrollbar _mCS_1 mCS_no_scrollbar">
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
                            품번
                            <span class="table-required">필수항목</span>
                          </th>
                          <td>
                            <div class="item-input">
                              <input type="text" id="itemNo" v-model="selectedData.itemNo" />
                            </div>
                          </td>
                          <th>
                            품명
                            <span class="table-required">필수항목</span>
                          </th>
                          <td>
                            <div class="item-input">
                              <input type="text" v-model="selectedData.itemNm" />
                            </div>
                          </td>
                        </tr>
                        <tr>
                          <th>
                            구분
                            <span class="table-required">필수항목</span>
                          </th>
                          <td colspan="3">
                            <div class="item-input">
                              <input type="text" v-model="selectedData.itemKind" />
                            </div>
                          </td>
                        </tr>
                        <tr>
                          <th>
                            OEM 단가
                            <span class="table-required">필수항목</span>
                          </th>
                          <td>
                            <div class="item-input">
                              <input type="text" id="itemNo" v-model="selectedData.oemPrice" />
                            </div>
                          </td>
                          <th>
                            AS 단가
                            <span class="table-required">필수항목</span>
                          </th>
                          <td>
                            <div class="item-input">
                              <input type="text" v-model="selectedData.asPrice" />
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
                      <div class="mCSB_dragger_bar" style="line-height: 30px;"></div>
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
              >취소</button>
              <button type="button" class="btn btn-c-primary" @click="addData">확인</button>
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
  name: "mediaserver",
  data() {
    return {
      searchAry:['ITEM_NO', 'ITEM_NM'],
      listMeta: {
        dblclickCallback: function (vm, data) {
          vm.$options.parent.showLayerPopup(data);
        },
        meta: [
          {
            col: "input=checkbox",
            name: "",
            size: "50px",
            targetid: "chkgrid",
          },
          { col: "ITEM_NO", name: "품번", size: "300px" },
          { col: "ITEM_NM", name: "품명", size: "500px" },
          { col: "ITEM_KIND", name: "구분", size: "100px" },
          { col: "OEM_PRICE", name: "Oem 단가", size: "150px" },
          { col: "AS_PRICE", name: "As 단가", size: "" },
        ],
      },
      listData: [],
      searchListData: [],
      headerButtons: [
        {
          type: "normal",
          callback: function (vm) {
            vm.$options.parent.getSearch();
          },
          text: "Refresh",
        },
        {
          type: "normal",
          callback: function (vm, param) {
            console.log(param);
            vm.$options.parent.showLayerPopup();
          },
          text: "Add",
        },
        {
          type: "Import",
          callback: function (vm, file) {
            vm.$options.parent.upload(file);
          },
          text: "Excel upload",
        },
      ],
      totalCount: 0,
      paginYn: "N",
      mode: "",
      selectedData: {
        itemNo: "",
        itemNm: "",
        itemKind: "",
        oemPrice: 0,
        asPrice: 0,
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

      await this.getItemList();
    },
    async getItemList() {
      let me = this;
      carApi
        .getItemList({})
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
          alert("오류발생");
          console.error("getItemList:", error);
        });
    },
    showLayerPopup() {
      let layer = document.querySelector("#layerPopup");
      if (layer.style.display == "none") {
        let data;
        if (arguments[0]) {
          data = arguments[0];
          document.querySelector("#itemNo").setAttribute("disabled", true);
          this.selectedData.itemNo = data.ITEM_NO;
          this.selectedData.itemNm = data.ITEM_NM;
          this.selectedData.itemKind = data.ITEM_KIND;
          this.selectedData.oemPrice = data.OEM_PRICE;
          this.selectedData.asPrice = data.AS_PRICE;

          this.mode = "update";
        } else {
          this.mode = "insert";
        }

        layer.style.display = "block";
        layer.style.opacity = "100";
      } else {
        document.querySelector("#itemNo").removeAttribute("disabled", false);
        this.selectedData.itemNo = "";
        this.selectedData.itemNm = "";
        this.selectedData.itemKind = "";
        this.selectedData.oemPrice = "";
        this.selectedData.asPrice = "";

        layer.style.display = "none";
        layer.style.opacity = "0";
      }
    },
    addData() {
      if (!this.selectedData.itemNo) {
        alert("품번을 입력해주세요");
        return;
      }
      if (!this.selectedData.itemNm) {
        alert("품명을 입력해주세요");
        return;
      }
      if (!this.selectedData.itemKind) {
        alert("구분을 입력해주세요");
        return;
      }
      if (!this.selectedData.oemPrice) {
        alert("oem단가를 입력해주세요");
        return;
      }
      if (!this.selectedData.asPrice) {
        alert("as 단가를 입력해주세요");
        return;
      }

      let me = this;
      carApi
        .ItemInsert(this.selectedData)
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
    getListData(dataList, Name) {
      let cnt = dataList.length;
      let data;
      for (let i = 0; i < cnt; i++) {
        if (dataList[i].Name === Name) {
          data = dataList[i];
          break;
        }
      }
      return data;
    },
    upload(file) {
      var frm = new FormData();
      frm.append("file", file);
      let me = this;

      carApi
        .itemFileupload(frm)
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
