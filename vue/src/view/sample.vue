<template>
  <!-- contents -->
  <div class="contents-wrap">
    <!-- 공통영역 x -->
    <section class="contents">
      <form action>
        <div class="contents-info">
          <h3 class="contents-title">Entity 관리</h3>
          <p class="contents-path">Home &gt; NGSI-LD &gt; DATA Core &gt; Entity 관리</p>
        </div>
        <div class="table-search">
          <table>
            <colgroup>
              <col width="170px" />
              <col />
              <col width="230px" />
              <col />
            </colgroup>
            <tbody>
              <tr>
                <th>Type 정보</th>
                <td>
                  <div class="form-group">
                    <div class="item-select w150">
                      <select name id>
                        <option value selected>Type ID</option>
                      </select>
                    </div>
                    <div class="item-input form-flexible">
                      <input type="text" placeholder="입력" />
                    </div>
                  </div>
                </td>
                <th>Entity 정보</th>
                <td>
                  <div class="form-group">
                    <div class="item-select w150">
                      <select name id>
                        <option value selected>Entity ID</option>
                      </select>
                    </div>
                    <div class="item-input form-flexible">
                      <input type="text" placeholder="입력" />
                    </div>
                  </div>
                </td>
              </tr>
              <tr>
                <th>등록자</th>
                <td>
                  <div class="item-input">
                    <input type="text" placeholder="입력" />
                  </div>
                </td>
                <th>기간</th>
                <td>
                  <div class="form-group">
                    <div class="item-input item-input-date form-flexible">
                      <input type="text" readonly />
                    </div>
                    <span class="dash">~</span>
                    <div class="item-input item-input-date form-flexible">
                      <input type="text" readonly />
                    </div>
                    <ul class="list-period" data-button-toggle="wrapper">
                      <!-- 활성화 is-active 클래스 추가 -->
                      <li class="list-period-item list-period-item-today is-active">
                        <button
                          type="button"
                          class="list-period-item-btn"
                          data-button-toggle="button"
                        >오늘</button>
                      </li>
                      <li class="list-period-item list-period-item-week">
                        <button
                          type="button"
                          class="list-period-item-btn"
                          data-button-toggle="button"
                        >일주일</button>
                      </li>
                    </ul>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
          <div class="table-search-btn">
            <button type="button" class="btn btn-c-tertiary" @click="getSearch">Search</button>
          </div>
        </div>

        <gridmain
          :list-data="listData"
          :list-meta="listMeta"
          :cur-page="curPage"
          :total-count="totalCount"
          :record-count="recordCount"
          :page-count="pageCount"
          :header-buttons="headerButtons"
          :paging-yn="paginYn"
          @movePage="movePage"
          gridId="grid1"
          headerId="header1"
          pagingId="paging1"
        />
      </form>
    </section>
    <!-- //공통영역 x -->

    <!-- layer popup영역 -->
    <div
      id="layerPopup"
      class="layer-wrap"
      data-layer="modal-find-id"
      tabindex="0"
      style="display: none; z-index: 10005; opacity: 0;"
    >
      <div class="layer-container">
        <section class="ui-layer">
          <div class="ui-layer__head">
            <h2 class="ui-layer__title">아이디 찾기</h2>
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
                      </colgroup>
                      <tbody>
                        <tr>
                          <th>
                            이름
                            <span class="table-required">필수항목</span>
                          </th>
                          <td>
                            <div class="item-input">
                              <input type="text" placeholder="입력" />
                            </div>
                          </td>
                        </tr>
                        <tr>
                          <th>
                            이메일주소
                            <span class="table-required">필수항목</span>
                          </th>
                          <td>
                            <div class="item-input">
                              <input type="text" placeholder="입력" />
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
              <button type="button" class="btn btn-c-primary">확인</button>
            </div>
          </div>
          <button type="button" class="ui-button ui-layer__close" data-role="layerClose">
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
export default {
  name: "sample",
  data() {
    return {
      listMeta: {
        callback: function (vm, param) {
          console.log(vm, param);
          vm.$options.parent.showLayerPopup();
        },
        meta: [
          {
            col: "input=checkbox",
            name: "",
            size: "100px",
            targetid: "parkdam",
          },
          { col: "a", name: "AA", size: "100px" },
          { col: "b", name: "BB", size: "" },
          { col: "c", name: "cc", size: "" },
          {
            col: "input=button",
            name: "삭제",
            size: "100px",
            callback: function (vm, param) {
              console.log(vm, param);
              alert("aasd");
            },
          },
        ],
      },
      listData: [],
      headerButtons: [
        {
          type: "normal",
          callback: function (vm, param) {
            console.log(param);
            vm.$options.parent.showLayerPopup();
          },
          text: "Add",
        },
        {
          type: "checkbox",
          callback: function (param, vm) {
            vm.$ds
              .get("deleteMap", { param: param })
              .then((data) => {
                console.log(data);
              })
              .catch((e) => {
                console.log(e);
              });
          },
          text: "Remove",
        },
        {
          type: "",
          callback: function (vm, param) {
            console.log(param);
            vm.$options.parent.showLayerPopup();
          },
          text: "Import",
        },
        {
          type: "normal",
          callback: function (vm, param) {
            console.log(param);
            vm.$options.parent.showLayerPopup();
          },
          text: "export",
        },
      ],
      totalCount: 0,
      recordCount: 10,
      pageCount: 10,
      curPage: 1,
      paginYn: "Y",
    };
  },
  created() {},
  mounted() {
    setTimeout(this.getSearch, 500);
  },
  updated() {},
  methods: {
    getSearch() {
      this.curPage = 1;
      this.search();
    },
    search() {
      this.$ds
        .get("getmap", { goPage: this.curPage })
        .then((data) => {
          this.listData = [];
          this.listData = data.list;
          this.totalCount = data.totalCount;
          console.log(this.listData);
        })
        .catch((e) => {
          console.log(e);
        });
    },
    movePage(goPage) {
      this.curPage = goPage;
      this.search();
    },
    showLayerPopup() {
      let layer = document.querySelector("#layerPopup");
      if (layer.style.display == "none") {
        layer.style.display = "block";
        layer.style.opacity = "100";
      } else {
        layer.style.display = "none";
        layer.style.opacity = "0";
      }
    },
  },
};
</script>
