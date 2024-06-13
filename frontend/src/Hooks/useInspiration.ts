import axios from 'axios';

export const useInspiration = () => {
    const createInspiration = async (data: any) => {
        const response = await axios.post('http://localhost:8080/add/inspiration', data, { withCredentials: true });
        return response.data.id;
    };

    const uploadImage = async (file: File, inspirationId: string, type: 'hero' | 'detail') => {
        const formData = new FormData();
        formData.append('file', file);
        const response = await axios.post(`http://localhost:8080/upload/image?inspirationId=${inspirationId}&type=${type}`, formData, {
            headers: {
                'Content-Type': 'multipart/form-data',
            },
            withCredentials: true,
        });
        return response.data.url;
    };

    const updateInspiration = async (id: string, data: any) => {
        await axios.put(`http://localhost:8080/inspiration/${id}`, data, { withCredentials: true });
    };

    return { createInspiration, uploadImage, updateInspiration };
};
