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
        const formData = new FormData();
        formData.append('name', name);
        formData.append('tags', tags);
        formData.append('description', description);

        if (heroImage) {
            formData.append('heroImage', heroImage);
        }

        detailImages.forEach((file, index) => {
            formData.append(`detailImages[${index}]`, file);
        });

        try {
            await axios.post('http://localhost:8080/upload/image', formData);
            // Reset form after submission
            setName('');
            setTags('');
            setDescription('');
            setHeroImage(null);
            setDetailImages([]);
        } catch (error) {
            console.error('Error uploading images:', error);
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
