<template>
  <div>
    <div class="table-list-info">
      <p class="table-info-total">
        Total
        <strong>{{ totalCount }}</strong>
      </p>
      <div class="btns-box" :id="gridId + '_header'"></div>
    </div>
    <div class="table-list-box" style="overflow:auto;" :style="{ height: gridHeight }">
      <div>
        <div class="table-list" style="height:100%;">
          <table :id="gridId"></table>
        </div>
      </div>
    </div>

    <input type="file" :id="gridId + '_file'" style="display:none" />
    <alert :is-show="isAlertShow" :message="AlertMessage" @alertclose="alertClose"></alert>
    <div id="testLayer" style="position:absolute;visibility:hidden"></div>
  </div>
</template>

<script>
export default {
  name: "gridtree",
  props: {
    listData: Array,
    listMeta: Object,
    headerButtons: Array,
    gridId: String,
    gridHeight: String,
    totalCount: Number,
  },
  data: function () {
    return {
      totalPage: 0,
      totalGroup: 0,
      curGroup: 0,
      grid: null,
      AlertMessage: "",
      isAlertShow: "hidden",
      pagenavi: null,
      isDown: "",
      testLayer: null,
      selectData: null,
    };
  },
  watch: {
    listData: function () {
      this.createBody();
    },
  },
  computed: {},
  created() {},
  mounted() {
    this.pagenavi = document.querySelector("#" + this.gridId + "_paging");
    this.createHeaderButton();
    this.createHeader();
    this.testLayer = document.querySelector("#testLayer");
  },
  updated() {
    console.log("updated");
    //this.createBody();
    //this.createPageNavi();
  },
  methods: {
    createHeaderButton() {
      const div = document.querySelector("#" + this.gridId + "_header");
      let me = this;
      this.headerButtons.forEach((ele) => {
        let a = document.createElement("a");
        a.setAttribute("class", "btn btn-c-primary");
        a.innerText = ele.text;
        if (ele.type == "checkbox") {
          let targetid;
          let meta = this.listMeta.meta;
          for (let i = 0; i < meta.length; i++) {
            let ele = meta[i];
            if (ele.col === "input=checkbox") {
              targetid = ele.targetid;
              break;
            }
          }
          a.addEventListener("click", (e) => {
            e.stopPropagation();
            let trs = me.grid.querySelectorAll("tbody tr");
            let param = [];
            let checkboxs = me.grid.querySelectorAll("[id='" + targetid + "']");
            trs.forEach((ele, index) => {
              if (checkboxs[index].checked) {
                let data = JSON.parse(ele.dataset.grid);
                param.push(data);
              }
            });
            if (param.length === 0) {
              me.AlertMessage = "항목을 체크해주세요.";
              me.isAlertShow = "visible";
            }

            if (param.length > 0) {
              ele.callback(param, me);
            }
          });
        } else if (ele.type === "Import") {
          a.addEventListener("click", (e) => {
            e.stopPropagation();
            //let input = me.grid.getElementsByTagName("file");
            let input = document.getElementById(this.gridId + "_file");
            input.accept = "json/plain";

            input.onchange = function (e) {
              let file = e.target.files[0];
              let reader = new FileReader();
              reader.onload = function () {
                try {
                  let data = JSON.parse(reader.result);

                  ele.callback(me, data);
                } catch (e) {
                  console.log(e);
                }
              };

              reader.readAsText(file, "utf-f");
            };

            input.click();
          });
        } else {
          a.addEventListener("click", (e) => {
            e.stopPropagation();
            ele.callback(me, {});
          });
        }

        div.append(a);
      });
    },
    createHeader() {
      this.grid = document.querySelector("#" + this.gridId);

      let colgroup = document.createElement("colgroup");
      let thead = document.createElement("thead");
      let tr = document.createElement("tr");
      const meta = this.listMeta.meta;
      meta.forEach((ele) => {
        this.createCol(colgroup, ele, tr);
      });

      thead.append(tr);

      this.grid.append(colgroup);
      this.grid.append(thead);
    },
    createCol(colgroup, ele, tr) {
      let col = document.createElement("col");
      if (ele.size) col.style.width = ele.size;
      colgroup.append(col);

      let th = document.createElement("th");
      //const me = this;
      if (ele.col == "input=checkbox") {
        let checkbox = document.createElement("input");
        checkbox.setAttribute("type", "checkbox");
        checkbox.setAttribute("id", "totalCheck");
        checkbox.addEventListener("click", function (e) {
          e.stopPropagation();

          let checkboxs = document.querySelectorAll(
            "[id='" + ele.targetid + "']"
          );
          if (e.target.checked) {
            checkboxs.forEach((ele) => {
              ele.checked = true;
            });
          } else {
            checkboxs.forEach((ele) => {
              ele.checked = false;
            });
          }
        });
        th.append(checkbox);
      } else {
        th.innerHTML = ele.name;
      }

      tr.append(th);
    },
    createBody() {
      let tbody = document.createElement("tbody");
      let me = this;
      let tr;
      this.grid.childNodes.forEach((ele) => {
        if (ele.tagName === "TBODY") {
          ele.removeEventListener("dblclick", () => {});
          this.grid.removeChild(ele);
        }
      });

      const meta = this.listMeta.meta;

      this.listData.forEach((data) => {
        tr = document.createElement("tr");
        tr.setAttribute("data-grid", JSON.stringify(data));

        meta.forEach((meta) => {
          this.createTd(tr, data, meta);
        });

        tr.addEventListener("dblclick", function () {
          if (this.listMeta.callback) {
            this.listMeta.callback(me, data);
          }
        });
        tbody.append(tr);
      });

      this.grid.append(tbody);
    },
    createTd(tr, data, meta) {
      let td = document.createElement("td");
      td.setAttribute("class", "no-drag");
      let me = this;
      if (meta.col == "input=checkbox") {
        let checkbox = document.createElement("input");
        checkbox.setAttribute("type", "checkbox");
        checkbox.setAttribute("id", meta.targetid);
        checkbox.addEventListener("click", (e) => {
          let data = JSON.parse(e.target.parentNode.parentNode.dataset.grid);
          let checked = e.target.checked;
          let resulttree2 = [];
          me.searchTree(resulttree2, data);

          let index = e.target.parentNode.parentNode.rowIndex;
          let trs = e.target.parentNode.parentNode.parentNode.childNodes;
          let cnt = index + resulttree2.length - 1;
          let chk;
          for (let i = index; i < cnt; i++) {
            chk = trs[i].firstElementChild.firstElementChild;
            chk.checked = checked;
          }
        });
        td.append(checkbox);
      } else if (meta.col == "input=button") {
        let button = document.createElement("button");
        button.setAttribute("type", "button");
        button.setAttribute("class", "btn btn-c-secondary btn-size-sm");
        button.innerHTML = "delete";
        button.addEventListener("click", function (e) {
          let tr = e.target.parentNode.parentNode;
          let param = JSON.parse(tr.dataset.grid);
          meta.callback(me, param);
        });
        td.append(button);
      } else {
        td.setAttribute("align", "left");
        let me = this;
        td.addEventListener("mousedown", (e) => {
          e.stopPropagation();
          me.isDown = "Y";
          let data = JSON.parse(e.target.parentNode.dataset.grid);
          me.selectData = data;
          me.testLayer.innerHTML = data.DisplayName;
          me.testLayer.style.backgroundColor = "red";
          me.testLayer.style.left = e.clientX + "px";
          me.testLayer.style.top = e.clientY + "px";

          console.log(e);
        });
        td.addEventListener("mousemove", (e) => {
          e.stopPropagation();
          if (me.isDown === "Y") {
            console.log("move", e);
            me.testLayer.style.visibility = "visible";
            me.testLayer.style.left = e.clientX + "px";
            me.testLayer.style.top = e.clientY + "px";
          }
        });
        td.addEventListener("mouseup", (e) => {
          e.stopPropagation();
          me.isDown = "";
          me.testLayer.style.visibility = "hidden";

          if (e.target.parentNode.dataset.grid) {
            let data = JSON.parse(e.target.parentNode.dataset.grid);
            console.log(data);

            if (me.selectData.UniqueName === data.UniqueName) {
              return;
            }

            let index = e.target.parentNode.rowIndex - 1;
            for (let i = index; i >= 0; i--) {
              if (me.listData[i].Parent === "") {
                break;
              } else {
                if (me.listData[i].Parent === me.selectData.UniqueName) {
                  return;
                }
              }
            }

            me.selectData.Parent = data.UniqueName;

            let result = [];
            me.listData.forEach((ele) => {
              if (ele.UniqueName === me.selectData.UniqueName) {
                result.push(me.selectData);
              } else {
                result.push(ele);
              }
            });

            me.$options.parent.TreeSearch(result);
            me.selectData = null;
          }
        });
        if (data[meta.col]) {
          let txt = "";
          for (let i = 1; i <= data["level"]; i++) {
            txt +=
              "<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>";
          }

          if (data["IsFolder"]) {
            txt += "[[[p]]]";
          }
          txt += data[meta.col];
          td.innerHTML = txt;
        }
      }

      tr.append(td);
    },
    alertClose(data) {
      this.isAlertShow = data;
    },
    searchTree(resuLttree, element) {
      let data = this.searchChildData(element.UniqueName);
      if (data.length > 0) {
        element.isFolder = "Y";
        resuLttree.push(element);

        data.forEach((ele) => {
          ele.level = element.level + 1;
          //resuLttree.push(ele);
          this.searchTree(resuLttree, ele);
        });
      } else {
        resuLttree.push(element);
      }
    },
    searchChildData(id) {
      let retVal = this.listData.filter((ele) => {
        return ele.Parent === id;
      });
      return retVal;
    },
  },
};
</script>
<style type="text/css">
.no-drag {
  -ms-user-select: none;
  -moz-user-select: -moz-none;
  -webkit-user-select: none;
  -khtml-user-select: none;
  user-select: none;
}
</style>
