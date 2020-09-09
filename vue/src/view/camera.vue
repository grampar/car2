<template>
  <!-- contents -->
  <div class="contents-wrap">
    <!-- 공통영역 x -->
    <section class="contents">
      <div class="contents-info">
        <h3 class="contents-title">Camera</h3>
        <p class="contents-path">Home &gt; CA &gt; Camera</p>
      </div>
      <search
        :search-list-data="searchListData"
        :list-data="searchListData"
        @searchresult="searchResult"
        searchcolumn="Name"
      />

      <gridmain
        :list-data="listData"
        :list-meta="listMeta"
        :header-buttons="headerButtons"
        :paging-yn="paginYn"
        gridId="grid1"
      />

      <alert :is-show="isAlertShow" :message="AlertMessage" @alertclose="alertClose"></alert>
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
                                  v-for="item in mediaList"
                                  :key="item.Name"
                                  :value="item.Name"
                                >{{ item.Name }}</option>
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
                                  v-for="item in recordList"
                                  :key="item.Name"
                                  :value="item.Name"
                                >{{ item.Name }}</option>
                              </select>
                            </div>
                          </td>
                          <th>Record Storage</th>
                          <td colspan="2">
                            <div class="item-select is-selected">
                              <select v-model="selectedData.RecordStorage">
                                <option
                                  v-for="item in storageList"
                                  :key="item.Name"
                                  :value="item.Name"
                                >{{ item.Name }}</option>
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
                              <input type="text" value="1" disabled />
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
  name: "camera",
  data() {
    return {
      listMeta: {
        callback: function (vm, data) {
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
          { col: "Label", name: "LABEL", size: "150px" },
          { col: "ObjectTypeName", name: "OBJECT TYPE NAME", size: "200px" },
          { col: "GeoInfo", name: "GEO INFO", size: "100px" },
          { col: "rtsp", name: "RTSP", size: "" },
          { col: "RecordCamNo", name: "RECORD CAM NO", size: "" },
          { col: "MediaServer", name: "Related Media Servers", size: "" },
          { col: "RecordServer", name: "Related Record Servers", size: "" },
        ],
      },
      listData: [],
      searchListData: [],
      mediaList: [],
      videoList: [],
      recordList: [],
      storageList: [],
      selectVideoList: [],
      headerButtons: [
        {
          type: "normal",
          callback: function (vm) {
            vm.$options.parent.search();
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
                vm.$options.parent.getSearch();
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
                vm.$options.parent.getSearch();
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
            vm.$options.parent.reqDataList(
              "object.list",
              { query: { PlayerTypeName: "video" } },
              (mediaData) => {
                let content = JSON.stringify(mediaData);
                let a = document.createElement("a");
                let file = new Blob([content], { type: "application/json" });
                a.href = URL.createObjectURL(file);
                a.download = "camera.json";
                a.click();
              }
            );
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
    this.getSearch();
    //setTimeout(this.getSearch, 500);
  },
  mounted() {},
  updated() {},
  methods: {
    searchResult(searchData) {
      if (searchData) {
        this.listData.splice(0, this.listData.length);
        this.listData = searchData;
      } else {
        this.getVideoList();
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
    async search() {
      //params":{"query":{"PlayerTypeName":"video"}}
      await this.getVideoList();
      await this.getMediaServerList();
      await this.getRecordServerList();
      await this.getStorageList();
    },
    async getVideoList() {
      let me = this;
      await this.reqDataList(
        "object.list",
        { query: { PlayerTypeName: "video" } },
        (videoData) => {
          me.videoList.splice(0, me.videoList.length);

          if (videoData.totalCount) {
            me.totalCount = videoData.totalCount;
            me.videoList = videoData.list;
          } else {
            me.totalCount = videoData.length;
            me.videoList = videoData;
          }

          me.listData.splice(0, me.listData.length);
          me.searchListData.splice(0, me.searchListData.length);
          me.videoList.forEach((ele) => {
            let data = {};
            data._id = ele._id;
            data.Name = ele.Name;
            data.Label = ele.Label;
            data.ObjectTypeName = ele.ObjectTypeName;

            data.rtsp = ele.Properties.rtsp[0];

            if (ele.Relations.mediaserver)
              data.MediaServer = ele.Relations.mediaserver[0];
            if (ele.Relations.recordserver)
              data.RecordServer = ele.Relations.recordserver[0];

            me.listData.push(data);
            me.searchListData.push(data);
          });
        }
      );
    },
    async getMediaServerList() {
      let me = this;
      await this.reqDataList(
        "object.list",
        { query: { PlayerTypeName: "mediaserver" } },
        (mediaData) => {
          me.mediaList = mediaData;
        }
      );
    },
    async getRecordServerList() {
      let me = this;
      await this.reqDataList(
        "object.list",
        { query: { PlayerTypeName: "recordserver" } },
        (record) => {
          me.recordList = record;
        }
      );
    },

    async getStorageList() {
      let me = this;
      await this.reqDataList(
        "object.list",
        { query: { PlayerTypeName: "storage" } },
        (storage) => {
          me.storageList = storage;
        }
      );
    },
    showLayerPopup() {
      let layer = document.querySelector("#layerPopup");
      if (layer.style.display == "none") {
        let data;
        if (arguments[0]) {
          data = this.getListData(this.videoList, arguments[0].Name);

          this.selectedData.Name = data.Name;
          this.selectedData.Label = data.Label;
          this.selectedData.Description = data.Description;
          this.selectedData.ObjectType = data.ObjectType;
          this.selectedData.RtspUrl = data.Properties.rtsp[0];

          this.selectedData.Latitude = data.Properties.latitude[0];
          this.selectedData.Longitude = data.Properties.longitude[0];

          this.selectedData.Bitrate = data.Properties.bitrate[0];

          this.selectedData.Codec = data.Properties.codec[0];

          this.selectedData.RtspID = data.Properties.RtspID[0];

          if (data.Relations.mediaserver)
            this.selectedData.MediaServer = data.Relations.mediaserver[0];
          if (data.Relations.recordserver)
            this.selectedData.RecordServer = data.Relations.recordserver[0];
          if (data.Relations.recordstorage)
            this.selectedData.RecordStorage = data.Relations.recordstorage[0];

          this.selectedData.RtspPassword = data.Properties.RtspPassword[0];
          this.selectedData.ConnectMode = data.Properties.ConnectMode[0];
          this.selectedData.UsePtz = data.Properties.UsePTZ[0];
          this.selectedData.PtzDriver = data.Properties.PTZDriver[0];
          this.selectedData.UseRds = data.Properties.UseRDS[0];
          this.selectedData.Width = data.Properties.Width[0];
          this.selectedData.Height = data.Properties.Height[0];

          this.selectedData.FPS = data.Properties.FPS[0];
          this.selectedData.RetentPeriod = data.Properties.RetentionPeriod[0];
          this.selectedData.RecordType = data.Properties.RecordType[0];
          if (data.Properties.ByPass)
            this.selectedData.ByPass = data.Properties.ByPass[0];

          this.mode = "update";
          this.updateData = data;
        } else {
          this.mode = "insert";
        }

        layer.style.display = "block";
        layer.style.opacity = "100";
      } else {
        for (let attr in this.selectedData) {
          this.selectedData[attr] = "";
        }
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
      if (this.selectedData.Name === "") {
        this.AlertMessage = "Name값을 입력해주세요.";
        this.isAlertShow = "visible";
        return;
      }
      if (this.selectedData.Label === "") {
        this.AlertMessage = "Label값을 입력해주세요.";
        this.isAlertShow = "visible";
        return;
      }

      if (this.selectData.ObjectType === "") {
        this.AlertMessage = "Object Type을 입력해주세요.";
        this.isAlertShow = "visible";
        return;
      }

      if (this.selectData.RtspUrl === "") {
        this.AlertMessage = "Object Type을 입력해주세요.";
        this.isAlertShow = "visible";
        return;
      }

      let param, method;

      if (this.mode === "update") {
        method = "object.upsert";
        param = this.updateData;
        param.Label = this.selectedData.Label;
        param.Description = this.selectedData.Description;
        param.Properties.ByPass[0] = this.selectedData.ByPass;
        param.Properties.CameraID[0] = this.selectedData.CameraID;
        param.Properties.ConnectMode[0] = this.selectedData.ConnectMode;

        param.Properties.FPS[0] = this.selectedData.FPS;
        param.Properties.Height[0] = this.selectedData.Height;
        param.Properties.PTZDriver[0] = this.selectedData.PTZDriver;
        param.Properties.PlayerTypeName[0] = this.selectedData.PlayerTypeName;
        param.Properties.RecordCameraNumber[0] = this.selectedData.RecordCameraNumber;
        param.Properties.RecordStorageList[0] = this.selectedData.RecordStorageList;

        param.Properties.RecordType[0] = this.selectedData.RecordType;
        param.Properties.RecorderID[0] = this.selectedData.RecorderID;
        param.Properties.RetentionPeriod[0] = this.selectedData.RetentionPeriod;
        param.Properties.RtspID[0] = this.selectedData.RtspID;
        param.Properties.RtspPassword[0] = this.selectedData.RtspPassword;

        param.Properties.UsePTZ[0] = this.selectedData.UsePTZ;
        param.Properties.UseRDS[0] = this.selectedData.UseRDS;
        param.Properties.Width[0] = this.selectedData.Width;
        param.Properties.bitrate[0] = this.selectedData.Bitrate;
        param.Properties.codec[0] = this.selectedData.Codec;

        param.Properties.latitude[0] = this.selectedData.Latitude;
        param.Properties.longitude[0] = this.selectedData.Longitude;
        param.Properties.rtsp[0] = this.selectedData.RtspUrl;
        param.Properties.MediaServerIP[0] = this.getListData(
          this.mediaList,
          this.selectedData.MediaServer
        ).Properties.ip[0];
      } else {
        method = "object.save";
        param = {
          Description: this.selectedData.Description,
          Label: this.selectedData.Label,
          Name: this.selectedData.Name,
          ObjectTypeName: "camera",
          PlayerTypeName: "video",
          Properties: {
            ByPass: [this.selectedData.ByPass],
            CameraID: [this.selectedData.CameraID],
            ConnectMode: [this.selectedData.ConnectMode],
            FPS: [this.selectedData.FPS],
            Height: [this.selectedData.Height],
            PTZDriver: [this.selectedData.PtzDriver],
            PlayerTypeName: [this.selectedData.PlayerTypeName],
            RecordCameraNumber: [this.selectedData.RecordCameraNumber],
            RecordStorageList: [this.selectedData.RecordStorage],
            RecordType: [this.selectedData.RecordType],
            RecorderID: [this.selectedData.RecorderID],
            RetentionPeriod: [this.selectedData.RetentionPeriod],
            RtspID: [this.selectedData.RtspID],
            RtspPassword: [this.selectedData.RtspPassword],
            UsePTZ: [this.selectedData.UsePtz],
            UseRDS: [this.selectedData.UseRds],
            Width: [this.selectedData.Width],
            bitrate: [this.selectedData.Bitrate],
            codec: [this.selectedData.Codec],
            latitude: [this.selectedData.Latitude],
            longitude: [this.selectedData.Longitude],
            ref: [""],
            rtsp: [this.selectedData.RtspUrl],
            MediaServerIP: [
              this.getListData(this.mediaList, this.selectedData.MediaServer)
                .Properties.ip[0],
            ],
          },
          RelationSource: {
            From: [],
            Pair: [],
            To: [],
          },
          Relations: {
            mediaserver: [this.selectedData.MediaServer],
            recordserver: [this.selectedData.RecordServer],
          },
        };
      }
      let data = [];
      data.push(param);

      let mediaData = this.getListData(
        this.mediaList,
        this.selectedData.MediaServer
      );
      if (mediaData) {
        mediaData.Relations.camera.push(this.selectedData.Name);
        data.push(mediaData);
      }

      let recordData = this.getListData(
        this.recordList,
        this.selectedData.RecordServer
      );
      if (recordData) {
        recordData.Relations.camera.push(this.selectedData.Name);
        recordData.Relations.storage.push(this.selectedData.RecordStorage);
        data.push(recordData);
      }

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
    selectChange(value) {
      let ary = value.split(",");
      this.selectVideoList = ary;
    },
  },
};
</script>
