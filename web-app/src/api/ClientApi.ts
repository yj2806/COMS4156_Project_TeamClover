import axios from 'axios'; // If you're using Axios

export const baseURL = 'http://localhost:8080/';
const clientURL = baseURL + 'client/';

export type ClientType = {
    authentication: string;
    type: string;
};

export const createClient = async (input: ClientType) => {
    axios
        .post(clientURL + 'create', input, {
            headers: {
                'Content-Type': 'application/json',
            },
        })
        .then(() => {
            // return response;
        })
        .catch(() => {
            // Handle errors.
        });
};

export const deleteClient = async (id: string, auth: string) => {
    try {
        // Replace 'your-api-url' with your actual API endpoint
        const response = await axios.delete(`${clientURL}delete/${id}`, {
            params: {
                auth: auth,
            },
        });
        // Set the retrieved data to the state
        return response.data;
    } catch (error) {
        // Handle errors
        console.error('API Error:', error);
    }
};
