import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Container, TextInput, Textarea, Button, Group, Badge, FileInput } from '@mantine/core';
import { useForm } from "../Hooks/useForm.ts";
import { useInspiration } from "../Hooks/useInspiration.ts";


const AddInspirationPage: React.FC = () => {
    const navigate = useNavigate();
    const [formValues, handleFormChange] = useForm({ name: '', tags: '', description: '' });
    const [heroImage, setHeroImage] = useState<File | null>(null);
    const [detailImages, setDetailImages] = useState<File[]>([]);
    const [error, setError] = useState<string | null>(null);
    const { createInspiration, uploadImage, updateInspiration } = useInspiration();

    const handleSubmit = async () => {
        try {
            // Step 1: Add inspiration
            const inspirationData = {
                ...formValues,
                heroImage: '', // Placeholder
                detailsImageUrls: [], // Placeholder
                tags: formValues.tags.split(',').map(tag => tag.trim()),
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
