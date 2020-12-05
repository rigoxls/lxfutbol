const casual = require('casual')

module.exports = (params) => {
    var param = params;
    var i =0
    var random = casual.integer(from = 1, to = 20)
    casual.define("hotel", function() {
      return {
        number: casual.building_number,
        price: casual.integer(from = 100000, to = 10000000),
        type: param.type,
        address: casual.address,
        city: param.city,
        country: param.country
      };
    });
    const data = {
        hotels: []
    };
    // Create 100 users
    for ( i = 0; i < random; i++) {
      data.hotels.push(casual.hotel);
    }
    return data;
  };