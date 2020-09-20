import HTTP from "@/api";

const TargetURL = "/src";
const TargetURL2 = "/car";

export default {
  
  getSrcList: (payload) => {
    return HTTP.post(`${TargetURL}/getSrcList`, payload);
  },
  insertSrc: (payload) => {
    return HTTP.post(`${TargetURL}/insertSrc`, payload);
  },
  srcFileupload: (frm) => {
    return HTTP.post(`${TargetURL}/srcFileupload`, frm, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
  },
  getItemList: (payload) => {
    return HTTP.post(`${TargetURL2}/getItemList`, payload);
  },
  getSrcItemList: (payload) => {
    return HTTP.post(`${TargetURL}/getSrcItemList`, payload);
  },
  insertSrcItem: (payload) => {
    return HTTP.post(`${TargetURL}/insertSrcItem`, payload);
  },
  getSrcSumList: (payload) => {
    return HTTP.post(`${TargetURL}/getSrcSumList`, payload);
  },
  insertSrcSum: (payload) => {
    return HTTP.post(`${TargetURL}/insertSrcSum`, payload);
  },
  updateSrcSum: (frm) => {
    return HTTP.post(`${TargetURL}/updateSrcSum`, frm, {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    });
  },
};
