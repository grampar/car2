import HTTP from "@/api";

const TargetURL = "/car";

export default {
  getCarList: (payload) => {
    return HTTP.post(`${TargetURL}/getCarList`, payload);
  },
  insertCar: (payload) => {
    return HTTP.post(`${TargetURL}/insertCar`, payload);
  },  
  getCarMonCntList: (payload) => {
    return HTTP.post(`${TargetURL}/getCarMonCntList`, payload);
  },
  carFileupload: (frm) => {
    return HTTP.post(`${TargetURL}/insertCarMonCnt`, frm, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
  },
  getCarMonSumList: (payload) => {
    return HTTP.post(`${TargetURL}/getCarMonSumList`, payload);
  },
  insertCarMonSum: (payload) => {
    return HTTP.post(`${TargetURL}/insertCarMonSum`, payload);
  },
  getItemList: (payload) => {
    return HTTP.post(`${TargetURL}/getItemList`, payload);
  },
  ItemInsert: (payload) => {
    return HTTP.post(`${TargetURL}/ItemInsert`, payload);
  },
  itemFileupload: (frm) => {
    return HTTP.post(`${TargetURL}/itemUpload`, frm, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
  },
  getCarItemList: (payload) => {
    return HTTP.post(`${TargetURL}/getCarItemList`, payload);
  },
  insertCarItem: (payload) => {
    return HTTP.post(`${TargetURL}/insertCarItem`, payload);
  },

  getItemMonRcarList: (payload) => {
    return HTTP.post(`${TargetURL}/getItemMonRcarList`, payload);
  },
  itemMonRcarUpload: (frm) => {
    return HTTP.post(`${TargetURL}/itemMonRcarUpload`, frm, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
  },
  getItemRcarSumList: (payload) => {
    return HTTP.post(`${TargetURL}/getItemRcarSumList`, payload);
  },
  insertItemRcarSum: (payload) => {
    return HTTP.post(`${TargetURL}/insertItemRcarSum`, payload);
  },

  getItemMonCkdList: (payload) => {
    return HTTP.post(`${TargetURL}/getItemMonCkdList`, payload);
  },
  itemMonCkdUpload: (frm) => {
    return HTTP.post(`${TargetURL}/itemMonCkdUpload`, frm, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
  },
  getItemCkdSumList: (payload) => {
    return HTTP.post(`${TargetURL}/getItemCkdSumList`, payload);
  },
  insertItemCkdSum: (payload) => {
    return HTTP.post(`${TargetURL}/insertItemCkdSum`, payload);
  },


  /* AS */
  getItemMonAsList: (payload) => {
    return HTTP.post(`${TargetURL}/getItemMonAsList`, payload);
  },
  itemMonAsUpload: (frm) => {
    return HTTP.post(`${TargetURL}/itemMonAsUpload`, frm, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
  },
  getItemAsSumList: (payload) => {
    return HTTP.post(`${TargetURL}/getItemAsSumList`, payload);
  },
  insertItemAsSum: (payload) => {
    return HTTP.post(`${TargetURL}/insertItemAsSum`, payload);
  },

  /* mobis */
  getItemMonMobisList: (payload) => {
    return HTTP.post(`${TargetURL}/getItemMonMobisList`, payload);
  },
  itemMonMobisUpload: (frm) => {
    return HTTP.post(`${TargetURL}/itemMonMobisUpload`, frm, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
  },
  getItemAMobisSumList: (payload) => {
    return HTTP.post(`${TargetURL}/getItemMobisSumList`, payload);
  },
  insertItemMobisSum: (payload) => {
    return HTTP.post(`${TargetURL}/insertItemMobisSum`, payload);
  },


  getPriceSum: (payload) => {
    return HTTP.post(`${TargetURL}/getPriceSum`, payload);
  },

};
