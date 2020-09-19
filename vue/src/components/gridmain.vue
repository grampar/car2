<template>
  <div>
    <div class="table-list-info">
      <p class="table-info-total">
        {{ gridname }} | Total
        <strong>{{ totalCount }}</strong>
      </p>
      <div style="float:left;" v-if="excelMeta">
        <downloadExcel
          :data="listData"
          :fields="excelMeta"
          :worksheet="excelName"
          :name="excelName"
        >
          <span><button class="btn btn-c-primary">Excel Download</button></span>
        </downloadExcel>
      </div>
      <div class="btns-box" :id="gridId + '_header'"></div>
    </div>
    <div>
      <div class="table-list">
        <table :id="gridId + '_thader'"></table>
      </div>
    </div>
    <div
      class="table-list-box"
      style="overflow:auto;"
      :style="{ height: gridHeight }"
    >
      <div>
        <div class="table-list" style="height:100%;">
          <table :id="gridId"></table>
        </div>
      </div>

      <div
        class="table-pagenation"
        :id="gridId + '_paging'"
        style="display:none"
      >
        <div class="pagination-box">
          <button
            type="button"
            class="pagination-btn pagination-first"
          ></button>
          <button type="button" class="pagination-btn pagination-prev"></button>
          <div class="pagination-pages"></div>
          <button type="button" class="pagination-btn pagination-next"></button>
          <button type="button" class="pagination-btn pagination-last"></button>
        </div>
      </div>
    </div>

    <input type="file" :id="gridId + '_file'" style="display:none" />
  </div>
</template>

<script>
export default {
  name: "gridmain",
  props: {
    listData: Array,
    listMeta: Object,
    excelMeta: Object,
    excelName: String,
    curPage: Number,
    totalCount: Number,
    recordCount: Number,
    pageCount: Number,
    headerButtons: Array,
    pagingYn: String,
    gridId: String,
    gridHeight: String,
    gridname: String,
  },
  data: function() {
    return {
      totalPage: 0,
      totalGroup: 0,
      curGroup: 0,
      grid: null,
      pagenavi: null,
      selectedData: [],
      preTr: null,
    };
  },
  watch: {
    listData: function() {
      this.createBody();
      if (this.pagingYn === "Y") {
        this.createPageNavi();
      } else {
        this.pagenavi.setAttribute("style", "display:none");
      }
    },
  },
  computed: {},
  created() {},
  mounted() {
    this.pagenavi = document.querySelector("#" + this.gridId + "_paging");
    this.createHeaderButton();
    this.createHeader();
  },
  updated() {
    console.log("updated");
    //this.createBody();
    //this.createPageNavi();
  },
  methods: {
    createHeaderButton() {
      const div = document.querySelector("#" + this.gridId + "_header");
      let divForm = document.createElement("div");
      divForm.setAttribute("class", "form-group");

      let me = this;
      this.headerButtons.forEach((ele) => {
        if (ele.type == "date") {
          let selectdiv = document.createElement("div");
          selectdiv.setAttribute("class", "item-select w150 is-selected");
          let select = document.createElement("select");
          select.setAttribute("id", "damYear");
          select.setAttribute("style", "width:100px");
          const today = new Date();

          let startyear = 2020;
          let endyear = today.getFullYear();
          let option;
          for (let i = startyear; i <= endyear; i++) {
            option = document.createElement("option");
            option.innerHTML = i;
            option.value = i;
            if (endyear == i) {
              option.setAttribute("selected", true);
            }
            select.append(option);
          }
          selectdiv.append(select);

          divForm.append(selectdiv);

          selectdiv = document.createElement("span");
          selectdiv.setAttribute("class", "item-select is-selected");
          select = document.createElement("select");
          select.setAttribute("id", "damMon");
          select.setAttribute("style", "width:100px");

          let month = today.getMonth() + 1;
          let tmpi;
          for (let i = 1; i <= 12; i++) {
            option = document.createElement("option");

            tmpi = i.toString().length == 1 ? "0" + i : i.toString();
            option.innerHTML = tmpi;
            option.value = tmpi;
            if (month == i) {
              option.setAttribute("selected", true);
            }
            select.append(option);
          }
          selectdiv.append(select);
          divForm.append(selectdiv);
        } else {
          let btndiv = document.createElement("div");
          btndiv.setAttribute("class", "btns-box");

          let button = document.createElement("button");
          button.setAttribute("class", "btn btn-c-secondary");
          button.innerText = ele.text;

          //let input = me.grid.getElementsByTagName("file");

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
            button.addEventListener("click", (e) => {
              e.stopPropagation();
              let trs = me.grid.querySelectorAll("tbody tr");
              let param = [];
              let checkboxs = me.grid.querySelectorAll(
                "[id='" + targetid + "']"
              );
              trs.forEach((ele, index) => {
                if (checkboxs[index].checked) {
                  let data = JSON.parse(ele.dataset.grid);
                  param.push(data);
                }
              });
              if (param.length === 0) {
                alert("항목을 체크해주세요.");
              }

              if (param.length > 0) {
                ele.callback(param, me);
              }
            });
          } else if (ele.type === "Import") {
            let input = document.getElementById(this.gridId + "_file");
            input.addEventListener("change", (e) => {
              let file = e.target.files[0];
              ele.callback(me, file);
            });

            button.addEventListener("click", (e) => {
              e.stopPropagation();
              input.value = "";
              input.click();
            });
          } else {
            button.addEventListener("click", (e) => {
              e.stopPropagation();
              ele.callback(me, {});
            });
          }

          btndiv.append(button);
          divForm.append(btndiv);
        }

        div.append(divForm);
      });
    },
    createHeader() {
      this.gridthader = document.querySelector("#" + this.gridId + "_thader");
      this.grid = document.querySelector("#" + this.gridId);

      let colgroup = document.createElement("colgroup");
      let colgroup2 = document.createElement("colgroup");
      let thead = document.createElement("thead");
      let tr = document.createElement("tr");
      const meta = this.listMeta.meta;
      meta.forEach((ele) => {
        this.createCol(colgroup, colgroup2, ele, tr);
      });

      thead.append(tr);

      this.gridthader.append(colgroup);
      this.gridthader.append(thead);

      this.grid.append(colgroup2);
    },
    createCol(colgroup, colgroup2, ele, tr) {
      let col = document.createElement("col");
      let col2 = document.createElement("col");
      if (ele.size) {
        col.setAttribute("style", "width:" + ele.size);
        col2.setAttribute("style", "width:" + ele.size);
      }
      colgroup.append(col);
      colgroup2.append(col2);

      let th = document.createElement("th");
      //const me = this;
      if (ele.col == "input=checkbox") {
        let checkbox = document.createElement("input");
        checkbox.setAttribute("type", "checkbox");
        checkbox.setAttribute("id", "totalCheck");
        checkbox.addEventListener("click", function(e) {
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
          ele.removeEventListener("click", () => {});
          ele.removeEventListener("dblclick", () => {});
          this.grid.removeChild(ele);
        }
      });

      const meta = this.listMeta.meta;
      const clickcallback = this.listMeta.clickcallback;
      const dblclickCallback = this.listMeta.dblclickCallback;

      this.listData.forEach((data) => {
        tr = document.createElement("tr");
        tr.setAttribute("data-grid", JSON.stringify(data));
        tr.setAttribute("style", "cursor:pointer");
        meta.forEach((meta) => {
          this.createBodyTd(tr, data, meta);
        });

        if (dblclickCallback) {
          tr.addEventListener("dblclick", function() {
            dblclickCallback(me, data);
          });
        }

        tr.addEventListener("click", function(e) {
          e.stopPropagation();
          if (me.preTr) {
            me.preTr.setAttribute("style", "cursor:pointer;background:white");
          }
          let tr = me.searchTr(e.target.parentNode);

          tr.setAttribute("style", "cursor:pointer;background:red");
          if (clickcallback) {
            clickcallback(me, data);
          }

          me.preTr = tr;
        });

        tbody.append(tr);
      });

      this.grid.append(tbody);
    },
    searchTr(obj) {
      let tr;
      if (obj.tagName !== "TR") {
        this.searchTr(obj.parentNode);
      } else {
        tr = obj;
      }

      return tr;
    },
    createBodyTd(tr, data, meta) {
      let td = document.createElement("td");
      let me = this;
      if (meta.col == "input=checkbox") {
        let checkbox = document.createElement("input");
        checkbox.setAttribute("type", "checkbox");
        checkbox.setAttribute("id", meta.targetid);
        if (meta.name.toUpperCase() === "CHECKED") {
          if (data[meta.name.toUpperCase()] === "CHK") {
            checkbox.setAttribute("checked", true);
          }
        }        
        td.append(checkbox);
      } else if (meta.col == "input=button") {
        let button = document.createElement("button");
        button.setAttribute("type", "button");
        button.setAttribute("class", "btn btn-c-secondary btn-size-sm");
        button.innerHTML = "delete";
        button.addEventListener("click", function(e) {
          let tr = e.target.parentNode.parentNode;
          let param = JSON.parse(tr.dataset.grid);
          meta.callback(me, param);
        });
        td.append(button);
      } else {
        let txt = data[meta.col];
        if(txt){
          if (meta.limit) {
            txt = txt.substring(0, meta.limit) + "..";
          }
        }
        
        if (data[meta.col]) td.innerText = txt;
      }

      tr.append(td);
    },
    createPageNavi() {
      this.totalPage = Math.ceil(this.totalCount / this.recordCount);
      this.totalGroup = Math.ceil(this.totalPage / this.pageCount);

      this.renderPreBtn();
      this.renderPageNum();
      this.renderNextBtn();
    },
    renderPreBtn() {
      /**
       * 이전 // 처음
       */
      //let me = this;

      let firstBtn = this.pagenavi.querySelector(
        "button.pagination-btn.pagination-first"
      );
      firstBtn.removeEventListener("click", this.firstBtnClick);
      firstBtn.addEventListener("click", this.firstBtnClick);
      let preBtn = this.pagenavi.querySelector(
        "button.pagination-btn.pagination-prev"
      );
      preBtn.removeEventListener("click", this.preBtnClick);
      preBtn.addEventListener("click", this.preBtnClick);
      if (this.curPage > 1) firstBtn.disabled = false;
      else firstBtn.disabled = true;

      if (this.curPage > 1) preBtn.disabled = false;
      else preBtn.disabled = true;
    },
    firstBtnClick() {
      this.movePage(1);
    },
    preBtnClick() {
      this.movePage(this.curPage - 1);
    },
    renderPageNum() {
      /**
       * paging 첫번째 번호와 마지막 번호 계산
       */
      this.setCurGroup();
      let firstIndex = (this.curGroup - 1) * this.pageCount + 1;
      let lastIndex = this.curGroup * this.pageCount;
      if (this.curGroup == this.totalGroup) {
        lastIndex =
          (this.curGroup - 1) * this.pageCount +
          Math.ceil((this.totalCount % this.recordCount) / this.pageCount);
      }
      let me = this;
      let pageDiv = this.pagenavi.querySelector(".pagination-pages");

      // page num에 등록된 이벤트 및 dom을 제거한다.
      pageDiv.childNodes.forEach((ele, i) => {
        ele.removeEventListener("click", function(e) {
          me.numBtnClick(e, i);
        });
        pageDiv.removeChild(ele);
      });
      pageDiv.innerHTML = "";

      for (let i = firstIndex; i <= lastIndex; i++) {
        let btn = document.createElement("button");
        btn.textContent = i;
        btn.type = "button";

        btn.addEventListener("click", function(e) {
          me.numBtnClick(e, i);
        });

        if (i === this.curPage) {
          btn.setAttribute("class", "pagination-page active");
        } else {
          btn.setAttribute("class", "pagination-page");
        }

        pageDiv.append(btn);
      }
    },
    numBtnClick(e, i) {
      e.preventDefault;
      e.target.parentNode.childNodes.forEach((ele) => {
        ele.setAttribute("class", "pagination-page");
      });
      e.target.setAttribute("class", "pagination-page active");
      this.movePage(i);
    },
    renderNextBtn() {
      /**
       * 다음/ 마지막
       */
      //let me = this;
      let lastBtn = this.pagenavi.querySelector(
        "button.pagination-btn.pagination-last"
      );
      lastBtn.removeEventListener("click", this.lastBtnClick);
      lastBtn.addEventListener("click", this.lastBtnClick);
      let nextBtn = this.pagenavi.querySelector(
        "button.pagination-btn.pagination-next"
      );
      nextBtn.removeEventListener("click", this.nextBtnClick);
      nextBtn.addEventListener("click", this.nextBtnClick);

      if (this.curPage === this.totalPage) nextBtn.disabled = true;
      else nextBtn.disabled = false;

      if (this.curPage === this.totalPage) lastBtn.disabled = true;
      else lastBtn.disabled = false;
    },
    lastBtnClick() {
      this.movePage(this.totalPage);
    },
    nextBtnClick() {
      this.movePage(this.curPage + 1);
    },
    setCurGroup() {
      this.curGroup = Math.ceil(this.curPage / this.pageCount);
    },
    movePage(goPage) {
      this.curPage = goPage;
      this.setCurGroup();
      this.$emit("movePage", goPage);
    },
  },
};
</script>
<style>
.jb-table {
  display: table;
  width: 100%;
}
.jb-table-row {
  display: table-row;
}
.jb-table-cell {
  display: table-cell;
  padding: 0px 20px;
  /* height: 150px; */
}
.jb-top {
  vertical-align: top;
}
.jb-middle {
  vertical-align: middle;
}
.jb-bottom {
  vertical-align: bottom;
}
</style>
