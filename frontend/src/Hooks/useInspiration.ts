import axios from 'axios';

export const useInspiration = () => {
    const createInspiration = async (inspirationData: any): Promise<string> => {
        const response = await axios.post('http://localhost:8080/add/inspiration', inspirationData);
        return response.data.id;
    };

    const uploadImage = async (file: File, inspirationId: string, type: string): Promise<string> => {
        const formData = new FormData();
        formData.append('file', file);
        formData.append('inspirationId', inspirationId);
        formData.append('type', type);

        const response = await axios.post('http://localhost:8080/upload/image', formData, {
            headers: {
                'Content-Type': 'multipart/form-data',
            },
        });

        return response.data;
    };

    const updateInspiration = async (inspirationId: string, updatedInspirationData: any): Promise<void> => {
        await axios.put(`http://localhost:8080/inspiration/${inspirationId}`, updatedInspirationData);
    };

    return { createInspiration, uploadImage, updateInspiration };
};
