import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Container, TextInput, Textarea, Button, Group, Badge, FileInput } from '@mantine/core';
import axios from 'axios';

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

const createInspiration = async (inspirationData: any): Promise<string> => {
    const response = await axios.post('http://localhost:8080/add/inspiration', inspirationData);
    return response.data.id;
};

const updateInspiration = async (inspirationId: string, updatedInspirationData: any): Promise<void> => {
    await axios.put(`http://localhost:8080/inspiration/${inspirationId}`, updatedInspirationData);
};

const AddInspirationPage: React.FC = () => {
    const navigate = useNavigate();
    const [name, setName] = useState('');
    const [tags, setTags] = useState('');
    const [description, setDescription] = useState('');
    const [heroImage, setHeroImage] = useState<File | null>(null);
    const [detailImages, setDetailImages] = useState<File[]>([]);
    const [error, setError] = useState<string | null>(null);

    const handleSubmit = async () => {
        try {
            // Step 1: Add inspiration
            const inspirationData = {
                name,
                description,
                heroImage: '', // Placeholder
                detailsImageUrls: [], // Placeholder
                tags: tags.split(',').map(tag => tag.trim()),
            };

            const inspirationId = await createInspiration(inspirationData);
            console.log('Inspiration ID:', inspirationId); // Log the inspiration ID

            // Step 2: Upload hero image
            let heroImageUrl = '';
            if (heroImage) {
                heroImageUrl = await uploadImage(heroImage, inspirationId, 'hero');
                console.log('Hero Image URL:', heroImageUrl); // Log the hero image URL
            }

            // Step 3: Upload detail images
            const detailImageUrls = await Promise.all(detailImages.map(file => uploadImage(file, inspirationId, 'detail')));
            detailImageUrls.forEach(url => console.log('Detail Image URL:', url)); // Log each detail image URL

            // Step 4: Update inspiration with image URLs
            const updatedInspirationData = {
                ...inspirationData,
                heroImage: heroImageUrl,
                detailsImageUrls: detailImageUrls,
            };

            await updateInspiration(inspirationId, updatedInspirationData);

            // Reset form and navigate to another page if necessary
            setName('');
            setTags('');
            setDescription('');
            setHeroImage(null);
            setDetailImages([]);
            navigate('/');
        } catch (error) {
            console.error('Error adding inspiration:', error.response?.data || error.message);
            setError('Failed to add inspiration. Please try again.');
        }
    };

    return (
        <Container>
            <TextInput
                label="Image Title"
                value={name}
                onChange={(event) => setName(event.currentTarget.value)}
            />
            <TextInput
                label="Tags"
                value={tags}
                onChange={(event) => setTags(event.currentTarget.value)}
            />
            <Group>
                {tags.split(',').map((tag) => (
                    <Badge key={tag} color="pink" variant="light">
                        {tag}
                    </Badge>
                ))}
            </Group>
            <Textarea
                label="Description"
                value={description}
                onChange={(event) => setDescription(event.currentTarget.value)}
            />
            <FileInput label="Hero Image" onChange={setHeroImage} />
            <FileInput
                label="Detail Images"
                multiple
                onChange={(files) => setDetailImages(Array.from(files))}
            />
            <Button onClick={handleSubmit}>Add Inspiration</Button>
            {error && <p style={{ color: 'red' }}>{error}</p>}
        </Container>
    );
};

export default AddInspirationPage;
