var ip = "13.67.185.229";
var protocol= window.location.protocol;
var port = "80"


export const environment = {
  production: true,
  APIEndPoint:  protocol+"//"+ip+":"+port+"/",
};
