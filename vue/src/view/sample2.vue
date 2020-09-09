<template>
  <!-- contents -->
  <div class="contents-wrap">
    <!-- 공통영역 x -->
    <section class="contents">
      <form action name="searchForm">
        <div class="contents-info">
          <h3 class="contents-title">Camera</h3>
          <p class="contents-path">Home &gt; CA &gt; Camera</p>
        </div>
        <div class="table-search">
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
                    <input type="text" id="searchTxt" />
                  </div>
                </td>
              </tr>
            </tbody>
          </table>
          <div class="table-search-btn">
            <button type="button" class="btn btn-c-tertiary" @click="getBtnSearch">Search</button>
          </div>
        </div>
        <div>
          <div style="float:left;width:50%;padding:2px">
            <gridmain
              :list-data="listData"
              :list-meta="listMeta"
              :header-buttons="headerButtons"
              :paging-yn="paginYn"
              gridId="grid1"
            />
          </div>
          <div style="float:right;width:50%;padding:2px">
            <gridmain
              :list-data="listData"
              :list-meta="listMeta2"
              :header-buttons="headerButtons"
              :paging-yn="paginYn"
              gridId="grid2"
            />
          </div>
        </div>

        <alert :is-show="isAlertShow" :message="AlertMessage" @alertclose="alertClose"></alert>
      </form>
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
            <h2 class="ui-layer__title">Camera 추가</h2>
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
                            Name
                            <span class="table-required">필수항목</span>
                          </th>
                          <td colspan="2">
                            <div class="item-input">
                              <input type="text" v-model="selectedData.Name" />
                            </div>
                          </td>
                          <th>
                            Label
                            <span class="table-required">필수항목</span>
                          </th>
                          <td colspan="2">
                            <div class="item-input">
                              <input type="text" v-model="selectedData.Label" />
                            </div>
                          </td>
                        </tr>
                        <tr>
                          <th>Description</th>
                          <td colspan="5">
                            <div class="item-input">
                              <input type="text" v-model="selectedData.Description" />
                            </div>
                          </td>
                        </tr>
                        <tr>
                          <th>
                            Object Type
                            <span class="table-required">필수항목</span>
                          </th>
                          <td colspan="2">
                            <div class="item-input">
                              <input type="text" value="camera" v-model="selectedData.ObjectType" />
                            </div>
                          </td>
                          <th>
                            Rtsp Url
                            <span class="table-required">필수항목</span>
                          </th>
                          <td colspan="2">
                            <div class="item-input">
                              <input type="text" v-model="selectedData.RtspUrl" />
                            </div>
                          </td>
                        </tr>
                        <tr>
                          <th>Latitude</th>
                          <td colspan="2">
                            <div class="item-input">
                              <input type="text" v-model="selectedData.Latitude" />
                            </div>
                          </td>
                          <th>Longitude</th>
                          <td colspan="2">
                            <div class="item-input">
                              <input type="text" v-model="selectedData.Longitude" />
                            </div>
                          </td>
                        </tr>
                        <tr>
                          <th>Bitrate</th>
                          <td colspan="2">
                            <div class="item-input">
                              <input type="text" v-model="selectedData.Bitrate" />
                            </div>
                          </td>
                          <th>Codec</th>
                          <td colspan="2">
                            <div class="item-input">
                              <input type="text" v-model="selectedData.Codec" />
                            </div>
                          </td>
                        </tr>

                        <tr>
                          <th>Rtsp ID</th>
                          <td colspan="2">
                            <div class="item-input">
                              <input type="text" v-model="selectedData.RtspID" />
                            </div>
                          </td>
                          <th>Rtsp Password</th>
                          <td colspan="2">
                            <div class="item-input">
                              <input type="text" v-model="selectedData.RtspPassword" />
                            </div>
                          </td>
                        </tr>
                        <tr>
                          <th>Media Server</th>
                          <td colspan="2">
                            <div class="item-select is-selected">
                              <select v-model="selectedData.MediaServer">
                                <option
                                  v-for="item in selectData.MediaList"
                                  :key="item.Name"
                                  :value="item.Name"
                                >{{ item }}</option>
                              </select>
                            </div>
                          </td>
                          <th>Connect Mode</th>
                          <td colspan="2">
                            <div class="item-select is-selected">
                              <select v-model="selectedData.ConnectMode">
                                <option
                                  v-for="item in selectData.ConnectMode"
                                  :key="item"
                                  :value="item"
                                >{{ item }}</option>
                              </select>
                            </div>
                          </td>
                        </tr>
                        <tr>
                          <th>Record Server</th>
                          <td colspan="2">
                            <div class="item-select is-selected">
                              <select v-model="selectedData.RecordServer">
                                <option
                                  v-for="item in selectData.RecordServerList"
                                  :key="item.Name"
                                  :value="item.Name"
                                >{{ item }}</option>
                              </select>
                            </div>
                          </td>
                          <th>Record Storage</th>
                          <td colspan="2">
                            <div class="item-select is-selected">
                              <select v-model="selectedData.RecordStorage">
                                <option
                                  v-for="item in selectData.RecordStorageList"
                                  :key="item.Name"
                                  :value="item.Name"
                                >{{ item }}</option>
                              </select>
                            </div>
                          </td>
                        </tr>
                        <tr>
                          <th>Use PTZ</th>
                          <td>
                            <div class="item-select is-selected">
                              <select v-model="selectedData.UsePtz">
                                <option
                                  v-for="item in selectData.UsePtz"
                                  :key="item"
                                  :value="item"
                                >{{ item }}</option>
                              </select>
                            </div>
                          </td>
                          <th>PTZ Driver</th>
                          <td>
                            <div class="item-select is-selected">
                              <select v-model="selectedData.PtzDriver">
                                <option
                                  v-for="item in selectData.PtzDriver"
                                  :key="item"
                                  :value="item"
                                >{{ item }}</option>
                              </select>
                            </div>
                          </td>
                          <th>Use Rds</th>
                          <td>
                            <div class="item-select is-selected">
                              <select v-model="selectedData.UseRds">
                                <option
                                  v-for="item in selectData.UseRds"
                                  :key="item"
                                  :value="item"
                                >{{ item }}</option>
                              </select>
                            </div>
                          </td>
                        </tr>
                        <tr>
                          <th>Width</th>
                          <td>
                            <div class="item-input">
                              <input type="text" v-model="selectedData.Width" />
                            </div>
                          </td>
                          <th>Height</th>
                          <td>
                            <div class="item-input">
                              <input type="text" v-model="selectedData.Height" />
                            </div>
                          </td>
                          <th>FPS</th>
                          <td>
                            <div class="item-input">
                              <input type="text" v-model="selectedData.FPS" />
                            </div>
                          </td>
                        </tr>
                        <tr>
                          <th>Record Cam Num</th>
                          <td>
                            <div class="item-input">
                              <input type="text" id="RtspPassword" disabled />
                            </div>
                          </td>
                          <th>Retent Period</th>
                          <td>
                            <div class="item-input">
                              <input type="text" v-model="selectedData.RetentPeriod" />
                            </div>
                          </td>
                          <th>Record Type</th>
                          <td>
                            <div class="item-select is-selected">
                              <select v-model="selectedData.RecordType">
                                <option
                                  v-for="item in selectData.RecordType"
                                  :key="item"
                                  :value="item"
                                >{{ item }}</option>
                              </select>
                            </div>
                          </td>
                        </tr>
                        <tr>
                          <th>By Pass</th>
                          <td colspan="5">
                            <div class="item-select is-selected">
                              <select v-model="selectedData.ByPass">
                                <option
                                  v-for="item in selectData.ByPass"
                                  :key="item"
                                  :value="item"
                                >{{ item }}</option>
                              </select>
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
  name: "sample2",
  data() {
    return {
      listMeta: {
        callback: function (vm, data) {
          console.log(vm, data);
          vm.$options.parent.showLayerPopup(data);
        },
        meta: [
          {
            col: "input=checkbox",
            name: "",
            size: "100px",
            targetid: "chkgrid1",
          },
          { col: "Name", name: "NAME", size: "150px" },
          { col: "ip", name: "IP", size: "200px" },
          { col: "port", name: "PORT", size: "100px" },
          { col: "camera", name: "Related Cameras", size: "" },
        ],
      },
      listMeta2: {
        callback: function (vm, data) {
          console.log(vm, data);
          vm.$options.parent.showLayerPopup(data);
        },
        meta: [
          {
            col: "input=checkbox",
            name: "",
            size: "100px",
            targetid: "chkgrid2",
          },
          { col: "Name", name: "NAME", size: "150px" },
          { col: "ip", name: "IP", size: "200px" },
          { col: "port", name: "PORT", size: "100px" },
          { col: "camera", name: "Related Cameras", size: "" },
        ],
      },
      listData: [],
      searchListData: [],
      mediaList: [],
      videoList: [],
      selectVideoList: [],
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
            let names = [];
            param.forEach((ele) => {
              names.push(ele.Name);
            });
            vm.$ds
              .get("object.remove", { Name: names })
              .then((data) => {
                console.log(data);
                vm.getSearch();
              })
              .catch((e) => {
                console.log(e);
              });
          },
          text: "Remove",
        },
        {
          type: "Import",
          callback: function (vm, param) {
            console.log(param);
            vm.$ds
              .get("object.upsert", { data: param })
              .then((data) => {
                console.log(data);
                vm.getSearch();
              })
              .catch((e) => {
                console.log(e);
              });
          },
          text: "Import",
        },
        {
          type: "normal",
          callback: function (vm, param) {
            console.log(vm, param);
            vm.$options.parent.getMediaList((mediaData) => {
              let content = JSON.stringify(mediaData);
              let a = document.createElement("a");
              let file = new Blob([content], { type: "application/json" });
              a.href = URL.createObjectURL(file);
              a.download = "mediaserver.json";
              a.click();
            });
          },
          text: "export",
        },
      ],
      totalCount: 0,
      recordCount: 10,
      pageCount: 10,
      curPage: 1,
      paginYn: "N",
      AlertMessage: "",
      isAlertShow: "hidden",
      mode: "",
      updateData: null,
      selectData: {
        UsePtz: ["False", "True"],
        PtzDriver: [
          "onvif",
          "axis",
          "ciscovsm7",
          "nice",
          "truen",
          "mirasys",
          "milestone",
          "honeywell",
          "cpplus",
          "videonetics",
        ],
        UseRds: ["No", "S/W", "H/W"],
        ConnectMode: ["UDP", "TCP", "Multicast"],
        RecordType: ["Always", "Never", "Schedule"],
        ByPass: ["False", "True"],
      },
      selectedData: {
        Name: "",
        Label: "",
        Description: "",
        ObjectType: "",
        RtspUrl: "",
        Latitude: "",
        Longitude: "",
        Bitrate: "",
        Codec: "",
        RtspID: "",
        RtspPassword: "",
        MediaServer: "",
        ConnectMode: "",
        RecordServer: "",
        RecordStorage: "",
        UsePtz: "",
        PtzDriver: "",
        UseRds: "",
        Width: "",
        Height: "",
        FPS: "",
        RetentPeriod: "",
        RecordType: "",
        ByPass: "",
      },
    };
  },
  created() {
    setTimeout(this.getSearch, 500);
  },
  mounted() {},
  updated() {},
  methods: {
    getBtnSearch() {
      let searchTxt = document.querySelector("#searchTxt").value.trim();

      if (searchTxt) {
        let newData = this.searchListData.filter((ele) => {
          var re = new RegExp(searchTxt);
          return re.test(ele.Name);
        });
        this.listData = [];
        this.listData = newData;
      } else {
        this.listData = [];
        this.listData = this.searchListData;
      }
    },
    getSearch() {
      this.curPage = 1;
      this.search();
    },

    movePage(goPage) {
      this.curPage = goPage;
      this.search();
    },
    search() {
      //params":{"query":{"PlayerTypeName":"video"}}
      let me = this;
      this.getMediaList((mediaData) => {
        this.mediaList.splice(0, this.mediaList.length);

        if (mediaData.totalCount) {
          this.totalCount = mediaData.totalCount;
          this.mediaList = mediaData.list;
        } else {
          this.totalCount = mediaData.length;
          this.mediaList = mediaData;
        }

        this.listData.splice(0, this.listData.length);
        this.mediaList.forEach((ele) => {
          let data = {};
          data._id = ele._id;
          data.Name = ele.Name;
          data.Label = ele.Label;
          data.ip = ele.Properties.ip[0];
          data.port = ele.Properties.port[0];

          let camString = "";
          ele.Relations.camera.forEach((cam, index) => {
            if (index == 0) camString += cam;
            else camString += "," + cam;
          });
          data.camera = camString;
          this.listData.push(data);
          this.searchListData.push(data);
        });

        this.getCameraList((videoData) => {
          me.videoList = videoData;
        });
      });
    },

    showLayerPopup() {
      let layer = document.querySelector("#layerPopup");
      if (layer.style.display == "none") {
        let data;
        if (arguments[0]) {
          data = this.getMediaData(arguments[0].Name);
          let name = document.querySelector("#Name");
          name.value = data.Name;
          name.setAttribute("disabled", true);
          document.querySelector("#Label").value = data.Label;
          document.querySelector("#Description").value = data.Description;
          document.querySelector("#ip").value = arguments[0].ip;
          document.querySelector("#port").value = arguments[0].port;

          //this.selectVideoList = [];
          data.Relations.camera.forEach((ele) => {
            this.selectVideoList.push(ele);
          });

          this.mode = "update";
          this.updateData = data;
        } else {
          this.mode = "insert";
        }

        layer.style.display = "block";
        layer.style.opacity = "100";
      } else {
        layer.style.display = "none";
        layer.style.opacity = "0";
      }
    },
    getMediaList(callback) {
      this.$ds
        .get("object.list", { query: { PlayerTypeName: "mediaserver" } })
        .then((mediaData) => {
          callback(mediaData);
        })
        .catch((e) => {
          console.log(e);
        });
    },
    getCameraList(callback) {
      this.$ds
        .get("object.list", { query: { PlayerTypeName: "video" } })
        .then((videoData) => {
          callback(videoData);
        })
        .catch((e) => {
          console.log(e);
        });
    },
    getRecordList(callback) {
      this.$ds
        .get("object.list", { query: { PlayerTypeName: "recordserver" } })
        .then((videoData) => {
          callback(videoData);
        })
        .catch((e) => {
          console.log(e);
        });
    },
    getStorageList(callback) {
      this.$ds
        .get("object.list", { query: { PlayerTypeName: "storage" } })
        .then((videoData) => {
          callback(videoData);
        })
        .catch((e) => {
          console.log(e);
        });
    },
    addMediaServer() {
      let Name = document.querySelector("#Name");
      let Label = document.querySelector("#Label");
      let Description = document.querySelector("#Description");
      let ip = document.querySelector("#ip");
      let port = document.querySelector("#port");
      let cameras = this.selectVideoList;

      if (Name.value.trim() === "") {
        this.AlertMessage = "Name값을 입력해주세요.";
        this.isAlertShow = "visible";
        Name.focus();
        return;
      }
      if (Label.value.trim() === "") {
        this.AlertMessage = "Label값을 입력해주세요.";
        this.isAlertShow = "visible";
        Label.focus();
        return;
      }

      if (ip.value.trim() === "") {
        this.AlertMessage = "IP값을 입력해주세요.";
        this.isAlertShow = "visible";
        ip.focus();
        return;
      }
      if (port.value.trim() === "") {
        this.AlertMessage = "port값을 입력해주세요.";
        this.isAlertShow = "visible";
        port.focus();
        return;
      }
      let param, method;
      if (this.mode === "update") {
        method = "object.upsert";
        param = this.updateData;
        param.Label = Label.value;
        param.Description = Description.value;
        param.Properties.ip[0] = ip.value;
        param.Properties.port[0] = port.value;
        console.log(param);
      } else {
        method = "object.save";
        param = {
          Name: Name.value,
          Label: Label.value,
          Description: Description.value,
          Relations: { camera: [] },
          Properties: {
            ip: [ip.value],
            port: [port.value],
            map: [""],
          },
          ObjectTypeName: "mediaserver",
          PlayerTypeName: "mediaserver",
        };
      }

      param.Relations.camera = cameras;
      let data = [];
      data.push(param);
      cameras.forEach((id) => {
        data.push(this.getCameraData(id));
      });

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
    getCameraData(id) {
      let cnt = this.videoList.length;
      let camera;
      for (let i = 0; i < cnt; i++) {
        if (this.videoList[i]._id === id) {
          camera = this.videoList[i];
          break;
        }
      }
      return camera;
    },
    getMediaData(Name) {
      let cnt = this.mediaList.length;
      let media;
      for (let i = 0; i < cnt; i++) {
        if (this.mediaList[i].Name === Name) {
          media = this.mediaList[i];
          break;
        }
      }
      return media;
    },
    alertClose(data) {
      this.isAlertShow = data;
    },
    selectChange(value) {
      let ary = value.split(",");
      this.selectVideoList = ary;
    },
  },
};
</script>
