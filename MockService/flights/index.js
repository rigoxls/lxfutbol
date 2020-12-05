const casual = require('casual')

module.exports = ( params) => {
    var param2=params;
    var i =0
    var random = casual.integer(from = 1, to = 15)
    casual.define("flight", function() {
      return {
        cabin: casual.zip,
        arrivingDate: random.departinDate,
        price: casual.integer(from = 100000, to = 1000000),
        arrivingCity: param2.arrivingCity,
        meals: casual.integer(from = 0, to = 5),
        departinDate:  params.departinDate,
        departinCity: param2.departinCity,
        number: casual.building_number
      };
    });
    const data = {
        flights: []
    };
    // Create 100 users
    for ( i = 0; i < random; i++) {
      data.flights.push(casual.flight);
    }
    return data;
  };