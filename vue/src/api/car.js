import HTTP from "@/api";

const TargetURL = "/car";

export default {
  // 로그인
  getCarList: (payload) => {
    return HTTP.post(`${TargetURL}/getCarList`, payload);
  },
};
