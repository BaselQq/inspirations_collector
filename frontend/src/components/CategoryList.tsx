import { Card, Text } from '@mantine/core';

const CategoryList = () => {
    return (
        <Card withBorder p="md">
            <Text size="lg" weight={500} mb="sm">Category</Text>
            <Text>Web Design</Text>
            <Text>Video Editing</Text>
            <Text>Logo Design</Text>
        </Card>
    );
};

export default CategoryList;
