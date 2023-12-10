// Contains code for talking to the server
async function makeRequest (method, path, body) {
  const options = {
    method:method,
    headers: {
      'Content-type': 'application/json',
    },
  };
  // if (localStorage.getItem('token')) {
  //   options.headers.Authorization = `Bearer ${localStorage.getItem('token')}`;
  // }
  if (method !== 'GET' && body !== undefined) {
    options.body = JSON.stringify(body);
  }
  // console.log('options',options)
  const response = await fetch('http://localhost:8080/' + path, options);
  const data = await response.json()
  return data;
}

export default makeRequest;
