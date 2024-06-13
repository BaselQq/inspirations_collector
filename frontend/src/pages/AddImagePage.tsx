import React, { useState } from 'react';
import { Container, TextInput, Textarea, Button, Group, Badge, FileInput } from '@mantine/core';
import axios from 'axios';

const AddImagePage: React.FC = () => {
    const [name, setName] = useState('');
    const [tags, setTags] = useState('');
    const [description, setDescription] = useState('');
    const [heroImage, setHeroImage] = useState<File | null>(null);
    const [detailImages, setDetailImages] = useState<File[]>([]);

    const handleSubmit = async () => {
        try {
            // Upload hero image
            let heroImageUrl = '';
            if (heroImage) {
                const heroFormData = new FormData();
                heroFormData.append('file', heroImage);
                const heroImageResponse = await axios.post('http://localhost:8080/upload/image', heroFormData, {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                    },
                    withCredentials: true,
                });
                heroImageUrl = heroImageResponse.data.url;
            }

            // Upload detail images
            const detailImageUrls = [];
            for (const file of detailImages) {
                const detailFormData = new FormData();
                detailFormData.append('file', file);
                const detailImageResponse = await axios.post('http://localhost:8080/upload/image', detailFormData, {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                    },
                    withCredentials: true,
                });
                detailImageUrls.push(detailImageResponse.data.url);
            }

            // Prepare data for adding inspiration
            const inspirationData = {
                name,
                description,
                heroImage: heroImageUrl,
                detailsImageUrls: detailImageUrls,
                tags: tags.split(',').map(tag => tag.trim()),
            };

            // Add inspiration
            const addInspirationResponse = await axios.post('http://localhost:8080/add/inspiration', inspirationData, {
                withCredentials: true,
            });

            // Reset form after submission
            setName('');
            setTags('');
            setDescription('');
            setHeroImage(null);
            setDetailImages([]);
        } catch (error) {
            console.error('Error uploading images or adding inspiration:', error.response?.data || error.message);
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
            <Button onClick={handleSubmit}>Add</Button>
        </Container>
    );
};

export default AddImagePage;
