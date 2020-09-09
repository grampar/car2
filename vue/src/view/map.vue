<template>
  <!-- contents -->
  <div>
    <!-- 공통영역 x -->
    <section class="contents">
      <div class="contents-info">
        <h3 class="contents-title">Map</h3>
        <p class="contents-path">Home &gt; CA &gt; Map</p>
      </div>
      <div style="display:flex;flex-direction: row">
        <div style="width:60%;padding:3px;">
          <search
            :search-list-data="CustomMap.searchListData"
            :list-data="CustomTree.searchListData"
            @searchresult="searchResultCustomMap"
            searchcolumn="DisplayName"
          />
          <gridmain
            :list-data="CustomMap.listData"
            :list-meta="CustomMap.listMeta"
            :header-buttons="CustomMap.headerButtons"
            :paging-yn="CustomMap.paginYn"
            gridId="grid1"
            grid-height="50vh"
          />
        </div>
        <div style="width:40%;padding:3px">
          <search
            :search-list-data="CustomTree.searchListData"
            :list-data="CustomTree.searchListData"
            @searchresult="searchResultCustomTree"
            searchcolumn="DisplayName"
          />
          <gridtree
            :list-data="CustomTree.listData"
            :list-meta="CustomTree.listMeta"
            :header-buttons="CustomTree.headerButtons"
            :paging-yn="CustomTree.paginYn"
            gridId="grid2"
            grid-height="50vh"
          />
        </div>
      </div>
      <alert :is-show="isAlertShow" :message="AlertMessage" @alertclose="alertClose"></alert>
      <confirm
        :isshow="confirmProp.isshow"
        :message="confirmProp.message"
        :callback="confirmProp.callback"
        @confirmclose="confirmClose"
      ></confirm>
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
            <h2 class="ui-layer__title">Map Info</h2>
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
                            Nap Name
                            <span class="table-required">필수항목</span>
                          </th>
                          <td colspan="3">
                            <div class="item-input">
                              <input type="text" id="DisplayName" />
                            </div>
                          </td>
                        </tr>
                        <tr>
                          <th>Description</th>
                          <td colspan="3">
                            <div class="item-input">
                              <input type="text" />
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
              <button type="button" class="btn btn-c-primary" @click="addMediaServer">확인</button>
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
export default {
  name: "map",
  data() {
    return {
      CustomMap: {
        listMeta: {
          callback: function (vm, data) {
            vm.$options.parent.showLayerPopup(data);
          },
          meta: [
            {
              col: "input=checkbox",
              name: "",
              size: "100px",
              targetid: "mapcheck",
            },
            { col: "DisplayName", name: "NAME", size: "150px" },
            { col: "Description", name: "Description", size: "200px" },
            { col: "ModifiedTime", name: "Last Modified Time", size: "100px" },
            { col: "ModifiedUser", name: "Last Modified User", size: "100px" },
            {
              col: "input=button",
              name: "Copy",
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
        customMapList: [],
        headerButtons: [
          {
            type: "normal",
            callback: function (vm) {
              vm.$options.parent.CustomMapSearch();
            },
            text: "Refresh",
          },
          {
            type: "normal",
            callback: function (vm, param) {
              console.log(param);
              vm.$options.parent.showLayerPopup();
            },
            text: "New",
          },
          {
            type: "checkbox",
            callback: function (param, vm) {
              if (param.length > 1) {
                vm.$options.parent.AlertMessage = "Map은 하나만 선택해주세요.";
                vm.$options.parent.isAlertShow = "visible";
                return;
              }
              let reqParam = {
                MapInfo: { UniqueName: param[0].UniqueName },
                ListGroupName: "CustomMap",
              };

              vm.$ds
                .get("map.download", reqParam)
                .then((data) => {
                  reqParam = data.MapInfo[0];
                  reqParam.ListGroupName = "CustomMap";
                  vm.$ds
                    .get("map.upsert", reqParam)
                    .then((data2) => {
                      console.log(data2);
                      vm.$options.parent.CustomMapSearch();
                    })
                    .catch((e) => {
                      console.log(e);
                    });
                })
                .catch((e) => {
                  console.log(e);
                });
            },
            text: "Copy",
          },
          {
            type: "checkbox",
            callback: function (param, vm) {
              let reqParam = {
                param: { ListGroupName: "CustomMap" },
                changed: [],
                removed: [],
              };

              param.forEach((ele) => {
                reqParam.push(ele);
              });
              vm.$ds
                .get("map.save", reqParam)
                .then((data) => {
                  console.log(data);
                  vm.$options.parent.CustomMapSearch();
                })
                .catch((e) => {
                  console.log(e);
                });
            },
            text: "Remove",
          },
          {
            type: "normal",
            callback: function (vm, param) {
              console.log(vm, param);
              vm.$options.parent.reqDataList(
                "map.export",
                { params: { ListGroupName: "CustomMap" } },
                (mediaData) => {
                  let content = JSON.stringify(mediaData);
                  let a = document.createElement("a");
                  let file = new Blob([content], { type: "application/json" });
                  a.href = URL.createObjectURL(file);
                  a.download = "Map.json";
                  a.click();
                }
              );
            },
            text: "export",
          },
          {
            type: "Import",
            callback: function (vm, param) {
              console.log(param);
              vm.$ds
                .get("object.upsert", { data: param })
                .then((data) => {
                  console.log(data);
                  vm.$options.parent.getSearch();
                })
                .catch((e) => {
                  console.log(e);
                });
            },
            text: "Import",
          },
          {
            type: "checkbox",
            callback: function (param, vm) {
              let checki = 0;
              let me = vm.$options.parent;
              let ele, tData;
              for (let i = 0; i < param.length; i++) {
                ele = param[i];
                for (let j = 0; j < me.CustomTree.listData.length; j++) {
                  tData = me.CustomTree.listData[j];
                  if (ele.UniqueName === tData.UniqueName) {
                    checki++;
                    break;
                  }
                }

                if (checki > 0) {
                  break;
                }
              }

              if (checki > 0) {
                me.AlertMessage = "이미등록된 Map이 존재합니다.";
                me.isAlertShow = "visible";
                return;
              }

              param.forEach((ele) => {
                me.CustomTree.listData.push(ele);
              });
            },
            text: ">>>>",
          },
        ],
        paginYn: "N",
      },
      CustomTree: {
        listMeta: {
          callback: function (vm, data) {
            vm.$options.parent.showLayerPopup(data);
          },
          meta: [
            {
              col: "input=checkbox",
              name: "",
              size: "100px",
              targetid: "chktree",
            },
            { col: "DisplayName", name: "NAME" },
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
        headerButtons: [
          {
            type: "normal",
            callback: function (vm) {
              vm.$options.parent.CustomTreeSearch();
            },
            text: "Refresh",
          },
          {
            type: "normal",
            callback: function (vm, param) {
              console.log(param);
              vm.$options.parent.customTreeSave(param, vm);
            },
            text: "Save",
          },
          {
            type: "checkbox",
            callback: function (param, vm) {
              console.log(param);
              let me = vm.$options.parent;
              me.confirmProp.message = "선택한 Map을 삭제하시겠습니까?";
              me.confirmProp.isshow = "visible";
              me.confirmProp.callback = function () {
                param.forEach((ele) => {
                  const idx = me.CustomTree.listData.findIndex((item) => {
                    return item.UniqueName === ele.UniqueName;
                  });
                  if (idx > -1) me.CustomTree.listData.splice(idx, 1);
                });
                me.customTreeSave(param, vm);
              };
            },
            text: "remove",
          },
        ],
        paginYn: "N",
      },
      AlertMessage: "",
      isAlertShow: "hidden",
      mode: "",
      confirmProp: {
        message: "",
        isshow: "hidden",
        callback: function () {},
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
    customTreeSave(param, vm) {
      let tmpListData = vm.$options.parent.CustomTree.listData;

      let reqParam = {
        ListInfo: tmpListData,
        ListGroupName: "CustomTree",
      };

      vm.$ds
        .get("map.setList", reqParam)
        .then((data) => {
          console.log(data);
          reqParam = {
            ListInfo: tmpListData,
            ListGroupName: "Map",
          };
          vm.$ds
            .get("map.setList", reqParam)
            .then((data) => {
              console.log(data);

              reqParam = {
                UniqueNameList: [],
                ListGroupName: "CustomTree",
              };

              let params = [];
              tmpListData.forEach((ele) => {
                reqParam.UniqueNameList.push(ele.UniqueName);
                params.push({
                  UniqueName: ele.UniqueName,
                  Parameters: {
                    Parent: ele.Parent,
                  },
                });
              });

              vm.$ds
                .get("map.setRefList", reqParam)
                .then((data) => {
                  console.log(data);
                  vm.$ds
                    .get("map.setParameter", params)
                    .then((data) => {
                      console.log(data);
                      vm.$options.parent.CustomTreeSearch();
                    })
                    .catch((e) => {
                      console.log(e);
                    });
                })
                .catch((e) => {
                  console.log(e);
                });
            })
            .catch((e) => {
              console.log(e);
            });
        })
        .catch((e) => {
          console.log(e);
        });
    },
    searchResultCustomMap(searchData) {
      if (searchData) {
        this.CustomMap.listData = searchData;
      } else {
        this.CustomMapSearch();
      }
    },
    searchResultCustomTree(searchData) {
      if (searchData) {
        this.CustomTree.listData = searchData;
      } else {
        this.CustomTreeSearch();
      }
    },
    getSearch() {
      this.curPage = 1;
      this.search();
    },

    async search() {
      //params":{"query":{"PlayerTypeName":"video"}}
      await this.CustomMapSearch();
      await this.CustomTreeSearch();
    },

    async CustomMapSearch() {
      let me = this;
      await this.reqDataList(
        "map.list",
        { params: { ListGroupName: "CustomMap" } },
        (data) => {
          me.CustomMap.customMapList.splice(
            0,
            me.CustomMap.customMapList.length
          );

          me.totalCount = data.length;
          me.CustomMap.customMapList = data;

          me.CustomMap.listData.splice(0, me.CustomMap.listData.length);
          me.CustomMap.searchListData.splice(
            0,
            me.CustomMap.searchListData.length
          );
          me.CustomMap.customMapList.forEach((ele) => {
            let data = {};
            data.UniqueName = ele.UniqueName;
            data.DisplayName = ele.DisplayName;
            let DataJson = JSON.parse(ele.DataJSON);
            data.Description = DataJson.Description;
            data.ModifiedTime = DataJson.ModifiedTime;
            data.ModifiedUser = DataJson.ModifiedUser;

            me.CustomMap.listData.push(data);
            me.CustomMap.searchListData.push(data);
          });
        }
      );
    },
    async CustomTreeSearch() {
      let me = this;
      await this.reqDataList(
        "map.list",
        { params: { ListGroupName: "CustomTree" } },
        (data) => {
          me.TreeSearch(data);
        }
      );
    },
    TreeSearch(data) {
      this.CustomTree.tmpList = [];
      this.CustomTree.tmpList = data;
      let resultData = [];
      this.sortTree(resultData);

      this.CustomTree.customTreeList.splice(
        0,
        this.CustomTree.customTreeList.length
      );
      this.CustomTree.searchListData.splice(
        0,
        this.CustomTree.searchListData.length
      );

      this.totalCount = resultData.length;
      this.CustomTree.customTreeList = resultData;

      this.CustomTree.listData.splice(0, this.CustomTree.listData.length);
      this.CustomTree.listData = this.CustomTree.customTreeList;
      this.CustomTree.searchListData = this.CustomTree.customTreeList;
    },
    showLayerPopup() {
      let layer = document.querySelector("#layerPopup");
      if (layer.style.display == "none") {
        let data;
        if (arguments[0]) {
          //data = this.getMediaData(arguments[0].Name);
          data = this.getListData(this.recordList, arguments[0].Name);
          document.querySelector("#Name").setAttribute("disabled", true);

          this.selectedData.Name = data.Name;
          this.selectedData.Label = data.Label;
          this.selectedData.Description = data.Description;
          this.selectedData.ip = data.Properties.IP[0];
          this.selectedData.IMSPServerPort = data.Properties.IMSPServerPort[0];
          this.selectedData.RestServerPort = data.Properties.RestServerPort[0];

          this.mode = "update";
          this.updateData = data;
        } else {
          this.mode = "insert";
        }

        layer.style.display = "block";
        layer.style.opacity = "100";
      } else {
        document.querySelector("#Name").removeAttribute("disabled", false);

        this.selectedData.Name = "";
        this.selectedData.Label = "";
        this.selectedData.Description = "";
        this.selectedData.ip = "";
        this.selectedData.IMSPServerPort = "";
        this.selectedData.RestServerPort = "";

        this.selectVideoList.splice(0, this.selectVideoList.length);
        this.selectStorage.splice(0, this.selectStorage.length);
        layer.style.display = "none";
        layer.style.opacity = "0";
      }
    },

    async reqDataList(method, param, callback) {
      await this.$ds
        .get(method, param)
        //.get("object.list", { query: { PlayerTypeName: "mediaserver" } })
        .then((mediaData) => {
          callback(mediaData);
        })
        .catch((e) => {
          console.log(e);
        });
    },
    addMediaServer() {
      if (!this.selectedData.Name) {
        this.AlertMessage = "Name값을 입력해주세요.";
        this.isAlertShow = "visible";
        return;
      }
      if (!this.selectedData.Label) {
        this.AlertMessage = "Label값을 입력해주세요.";
        this.isAlertShow = "visible";
        return;
      }

      if (!this.selectedData.ip) {
        this.AlertMessage = "IP값을 입력해주세요.";
        this.isAlertShow = "visible";
        return;
      }

      let param, method;
      if (this.mode === "update") {
        method = "object.upsert";
        param = this.updateData;
        param.Label = this.selectedData.Label;
        param.Description = this.selectedData.Description;
        param.Properties.IP[0] = this.selectedData.ip;
        param.Properties.IMSPServerPort[0] = this.selectedData.IMSPServerPort;
        param.Properties.RestServerPort[0] = this.selectedData.RestServerPort;
      } else {
        method = "object.save";
        param = {
          Name: this.selectedData.Name,
          Label: this.selectedData.Label,
          Description: this.selectedData.Description,
          Relations: {},
          Properties: {
            IMSPServerPort: [this.selectedData.IMSPServerPort],
            ip: [this.selectedData.ip],
            RecorderID: [this.selectedData.ip],
            RestServerPort: [this.selectedData.RestServerPort],
            map: [""],
          },
          ObjectTypeName: "recordserver",
          PlayerTypeName: "recordserver",
          PlayerProperties: null,
          RelationSource: {
            From: [],
            Pair: [],
            To: [],
          },
        };
      }

      let data = [];
      data.push(param);

      this.$ds
        .get(method, { data: data })
        .then((retVal) => {
          console.log(retVal);
          this.showLayerPopup();
          this.getSearch();
        })
        .catch((e) => {
          console.log(e);
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
    alertClose(data) {
      this.isAlertShow = data;
    },

    confirmClose() {
      this.confirmProp.isshow = "hidden";
      this.confirmProp.message = "";
      this.confirmProp.callback = function () {};
    },
    sortTree(resulttree, element) {
      if (element) {
        let data = this.searchChildData(element.UniqueName);
        if (data.length > 0) {
          element.IsFolder = true;
          resulttree.push(element);
          data.forEach((ele) => {
            ele.level = element.level + 1;
            //resuLttree.push(ele);
            this.sortTree(resulttree, ele);
          });
        } else {
          element.IsFolder = false;
          resulttree.push(element);
        }
      } else {
        let param;
        const body = this.CustomTree.tmpList;
        for (let i = 0; i < body.length; i++) {
          param = body[i];
          if (param.Parent === "") {
            param.level = 1;
            let data = this.searchChildData(param.UniqueName);
            if (data.length > 0) {
              param.IsFolder = true;
              resulttree.push(param);
              data.forEach((ele) => {
                ele.level = 2;
                this.sortTree(resulttree, ele);
              });
            } else {
              param.IsFolder = false;
              resulttree.push(param);
            }
          }
        }
      }
    },
    searchChildData(id) {
      let retVal = this.CustomTree.tmpList.filter((ele) => {
        return ele.Parent === id;
      });
      return retVal;
    },
  },
};
</script>
