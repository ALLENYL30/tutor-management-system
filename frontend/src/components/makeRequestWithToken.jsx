// Contains code for talking to the server
async function makeRequestWithToken (method, path, body, token) {
  const options = {
    method:method,
    headers: {
      'Content-type': 'application/json',
      token: token,
    },
  };
  
  if (method !== 'GET' && body !== undefined) {
    options.body = JSON.stringify(body);
  }

  // console.log('options',options)
  const response = await fetch('http://localhost:8080/' + path, options);
  const data = await response.json()
  return data;
}

export default makeRequestWithToken;
