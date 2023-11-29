import axios from 'axios'; // If you're using Axios

export const createClient = async (selectedFile: File) => {
    const formData = new FormData();
    formData.append('file', selectedFile);

    axios
        .post('localhost', formData)
        .then(() => {
            // Handle the server's response, such as success or error messages.
        })
        .catch(() => {
            // Handle errors.
        });
};

export const EditClient = async () => {
    try {
        // Replace 'your-api-url' with your actual API endpoint
        const response = await axios.post('localhost', prompt, {
            headers: {
                'Content-Type': 'application/json',
            },
        });

        // Set the retrieved data to the state
        return response.data;
    } catch (error) {
        // Handle errors
        console.error('API Error:', error);
    }
};
