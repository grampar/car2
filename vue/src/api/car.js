import HTTP from "@/api";

const TargetURL = "/car";

export default {
  getCarList: (payload) => {
    return HTTP.post(`${TargetURL}/getCarList`, payload);
  },
  carFileupload: (frm) => {
    return HTTP.post(`${TargetURL}/carUpload`, frm, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
  },
  getCarMonCntList: (payload) => {
    return HTTP.post(`${TargetURL}/getCarMonCntList`, payload);
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
};
