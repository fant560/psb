const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app) {
  app.use(
    '/ml',
    createProxyMiddleware({
      target: 'http://web:8000',
      changeOrigin: true,
    })
  );
  app.use(
    '/api',
    createProxyMiddleware({
        target: 'http://javabackend:8081',
        changeOrigin: true,
      }) 
  )
};