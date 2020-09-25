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
                <input
                  type="text"
                  v-model="searchData"
                  @keyup.enter="getBtnSearch"
                />
              </div>
            </td>
          </tr>
        </tbody>
      </table>
      <div class="table-search-btn">
        <button type="button" class="btn btn-c-tertiary" @click="getBtnSearch">
          Search
        </button>
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
    searchAry: Array,

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
        this.searchListData = this.listData;
        let newData = this.searchListData.filter((ele) => {
          var re = new RegExp(searchTxt);
          let col, retVal=false;
          for(let i=0;i<this.searchAry.length;i++){
            col=this.searchAry[i];
            if(re.test(ele[col])){
              retVal=true;
              break;
            }
          }
          
          //return re.test(ele[this.searchcolumn]) || ;
          return retVal;
        });

        this.$emit("searchresult", newData);
      } else {
        this.$emit("searchresult", "");
      }
    },
  },
};
</script>
