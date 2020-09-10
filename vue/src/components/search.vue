<template>
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
                <input type="text" v-model="searchData" @keyup.enter="getBtnSearch" />
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
</template>

<script>
export default {
  name: "search",
  props: {
    searchListData: Array,
    listData: Array,
    searchcolumn: String,
  },
  data() {
    return {
      searchData: "",
    };
  },
  created() {},
  mounted() {},
  updated() {},
  methods: {
    getBtnSearch() {
      let searchTxt = this.searchData.trim();
      if (searchTxt) {
        let newData = this.searchListData.filter((ele) => {
          var re = new RegExp(searchTxt);
          if (this.searchcolumn === "DisplayName") {
            return re.test(ele.DisplayName);
          } else {
            return re.test(ele.Name);
          }
        });

        this.$emit("searchresult", newData);
      } else {
        this.$emit("searchresult", "");
      }
    },
  },
};
</script>
