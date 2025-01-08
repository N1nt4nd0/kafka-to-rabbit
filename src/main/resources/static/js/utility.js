function restApiRequest({
                                url,
                                method = 'GET',
                                body = null,
                                headers = {},
                                successMessage = '',
                                errorMessage = '',
                                callbackFunction = null
                            }) {
    const defaultHeaders = {
        'Content-Type': 'application/json'
    };
    const combinedHeaders = {...defaultHeaders, ...headers};
    const options = {
        method: method,
        headers: combinedHeaders,
        body: body ? JSON.stringify(body) : null
    };
    fetch(url, options)
        .then(response => {
            return response.json().then(data => {
                if (response.ok) {
                    return data;
                }
                throw {message: errorMessage || 'Request failed', details: data};
            });
        })
        .then(data => {
            if (successMessage) {
                alert(`${successMessage}. Response: ${JSON.stringify(data)}`);
            }
            if (callbackFunction) {
                callbackFunction(data);
            }
        })
        .catch(error => {
            console.error('Error sending API request: ', error.message, error.details || 'No details');
            if (errorMessage) {
                alert(`${errorMessage}${error.details ? ` ${JSON.stringify(error.details)}` : ''}`);
            }
        });
}