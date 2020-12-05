const jsonServer = require('json-server')
const server = jsonServer.create()
const middlewares = jsonServer.defaults()
const port = process.env.PORT || 3000

server.use(jsonServer.bodyParser)
server.use(middlewares)

server.listen(port, () => {
 console.log('JSON Server is running')
})

server.post('/hotels', (request, response) => {
  if (request.method === 'POST') {
    console.log('Got body:', request.body);
    const params = request.body.params;
    const hotels = require('./hotels/index')(params)
    response.status(200).jsonp(hotels)
  }
 })

 server.post('/flights', (request, response) => {
  if (request.method === 'POST') {
    const params = request.body.params;
    const flights = require('./flights/index')(params)
    response.status(200).jsonp(flights)
  }
 })

 
 server.post('/spectacles', (request, response) => {
  if (request.method === 'POST') {
    console.log('Got body:', request.body);
    const params = request.body.params;
    const spectacles = require('./spectacles/index')(params)
    response.status(200).jsonp(spectacles)
  }
 })

 