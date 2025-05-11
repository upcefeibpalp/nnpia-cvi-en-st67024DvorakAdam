import { useForm } from "react-hook-form";
import { yupResolver } from "@hookform/resolvers/yup";
import * as yup from "yup";

// Define the form data type based on User entity
type UserFormData = {
  email: string;
  password: string;
  active: boolean;
};

// Validation schema
const userSchema = yup.object().shape({
  email: yup.string()
    .required("Email is required")
    .email("Invalid email format"),
  password: yup.string()
    .required("Password is required")
    .min(8, "Password must be at least 8 characters")
    .matches(
      /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{8,}$/,
      "Password must contain at least one uppercase letter, one lowercase letter, and one number"
    ),
  active: yup.boolean()
    .default(true)
});

interface AddUserFormProps {
  onSubmit: (data: UserFormData) => void;
}

export const AddUserForm = ({ onSubmit }: AddUserFormProps) => {
  const { 
    register, 
    handleSubmit, 
    formState: { errors },
    reset
  } = useForm<UserFormData>({
    resolver: yupResolver(userSchema),
    defaultValues: {
      active: true
    }
  });

  const handleFormSubmit = (data: UserFormData) => {
    onSubmit(data);
    reset();
  };

  return (
    <form onSubmit={handleSubmit(handleFormSubmit)} className="max-w-md mx-auto p-4 bg-white rounded shadow-md">
      <div className="mb-4">
        <label htmlFor="email" className="block text-gray-700 text-sm font-bold mb-2">
          Email
        </label>
        <input
          id="email"
          type="email"
          {...register("email")}
          className={`shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline ${
            errors.email ? "border-red-500" : ""
          }`}
        />
        {errors.email && (
          <p className="text-red-500 text-xs italic">{errors.email.message}</p>
        )}
      </div>

      <div className="mb-4">
        <label htmlFor="password" className="block text-gray-700 text-sm font-bold mb-2">
          Password
        </label>
        <input
          id="password"
          type="password"
          {...register("password")}
          className={`shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline ${
            errors.password ? "border-red-500" : ""
          }`}
        />
        {errors.password && (
          <p className="text-red-500 text-xs italic">{errors.password.message}</p>
        )}
      </div>

      <div className="mb-4 flex items-center">
        <input
          id="active"
          type="checkbox"
          {...register("active")}
          className="mr-2 leading-tight"
        />
        <label htmlFor="active" className="text-gray-700 text-sm font-bold">
          Active
        </label>
      </div>

      <div className="flex items-center justify-between">
        <button
          type="submit"
          className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded focus:outline-none focus:shadow-outline"
        >
          Add User
        </button>
      </div>
    </form>
  );
};
