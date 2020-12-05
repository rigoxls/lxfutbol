const casual = require('casual')

module.exports = (params) => {
    var param = params;
    var index =0
    var random = casual.integer(from = 1, to = 10)
    casual.define("spectacle", function() {
      return {
        date: casual.date(format = 'YYYY-MM-DD'),
        type: "Fútbol_"+index,
        price: casual.integer(from = 100000, to = 100000),
        description: "Partido "+ param.country +" vs "+ casual.country,
        city: param.city,
        country: param.country,
      };
    });
    const data = {
      spectacles: []
    };
    // Create 100 users
    for (index ; index < random; index++) {
      data.spectacles.push(casual.spectacle);
    }
    return data;
  };