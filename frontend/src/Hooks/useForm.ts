import { useState } from 'react';

export const useForm = (initialValues: any) => {
    const [values, setValues] = useState(initialValues);

    const handleChange = (name: string, value: any) => {
        setValues((prevValues: any) => ({
            ...prevValues,
            [name]: value,
        }));
    };

    return [values, handleChange] as const;
};
