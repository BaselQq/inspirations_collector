import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Container, TextInput, Textarea, Button, Group, Badge, FileInput } from '@mantine/core';
import { useForm } from "../Hooks/useForm.ts";
import { useInspiration } from "../Hooks/useInspiration.ts";
import { useAuth } from "../Hooks/useAuth.ts";

const AddInspirationPage: React.FC = () => {
    const navigate = useNavigate();
    const user = useAuth();
    const [formValues, handleFormChange] = useForm({ name: '', tags: '', description: '' });
    const [heroImage, setHeroImage] = useState<File | null>(null);
    const [detailImages, setDetailImages] = useState<File[]>([]);
    const [error, setError] = useState<string | null>(null);
    const { createInspiration, uploadImage, updateInspiration } = useInspiration();

    const handleSubmit = async () => {
        if (!user) {
            setError('You must be logged in to add an inspiration.');
            return;
        }

        try {
            const inspirationData = {
                ...formValues,
                heroImage: '',
                detailsImageUrls: [],
                tags: formValues.tags.split(',').map(tag => tag.trim()),
            };

            const inspirationId = await createInspiration(inspirationData);
            console.log('Inspiration ID:', inspirationId);

            let heroImageUrl = '';
            if (heroImage) {
                heroImageUrl = await uploadImage(heroImage, inspirationId, 'hero');
                console.log('Hero Image URL:', heroImageUrl);
            }

            const detailImageUrls = await Promise.all(detailImages.map(file => uploadImage(file, inspirationId, 'detail')));
            detailImageUrls.forEach(url => console.log('Detail Image URL:', url));

            const updatedInspirationData = {
                ...inspirationData,
                heroImage: heroImageUrl,
                detailsImageUrls: detailImageUrls,
            };

            await updateInspiration(inspirationId, updatedInspirationData);

            handleFormChange('name', '');
            handleFormChange('tags', '');
            handleFormChange('description', '');
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
                value={formValues.name}
                onChange={(event) => handleFormChange('name', event.currentTarget.value)}
            />
            <TextInput
                label="Tags"
                value={formValues.tags}
                onChange={(event) => handleFormChange('tags', event.currentTarget.value)}
            />
            <Group>
                {formValues.tags.split(',').map((tag) => (
                    <Badge key={tag} color="pink" variant="light">
                        {tag}
                    </Badge>
                ))}
            </Group>
            <Textarea
                label="Description"
                value={formValues.description}
                onChange={(event) => handleFormChange('description', event.currentTarget.value)}
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
