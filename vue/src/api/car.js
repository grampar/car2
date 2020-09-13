import HTTP from "@/api";

const TargetURL = "/car";

export default {
  
  getCarList: (payload) => {
    return HTTP.post(`${TargetURL}/getCarList`, payload);
  },
  carFileupload:(frm) =>{
    return HTTP.post(`${TargetURL}/carUpload`, frm, {
      headers:{
        'Content-Type':'multipart/form-data'
      }
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
  itemFileupload:(frm) =>{
    return HTTP.post(`${TargetURL}/itemUpload`, frm, {
      headers:{
        'Content-Type':'multipart/form-data'
      }
    });
  },
  getCarItemList: (payload) => {
    return HTTP.post(`${TargetURL}/getCarItemList`, payload);
  },
};
