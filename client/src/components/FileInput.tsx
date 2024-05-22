import { useState, FormEvent, ChangeEvent } from "react";
import { useFileStore } from "../store/file-store";

export default function FileInput() {
  const [file, setFile] = useState<File | null>(null);
  const [fileName, setFileName] = useState<string>("");
  const { uploadFile } = useFileStore();

  const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    if (file && fileName) {
      await uploadFile(file, fileName);
      setFileName("");
      setFile(null);
    } else {
      console.error("No file or filename provided");
    }
  };

  const handleFileChange = (event: ChangeEvent<HTMLInputElement>) => {
    if (event.target.files && event.target.files.length > 0) {
      setFile(event.target.files[0]);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="flex flex-col w-1/4 space-y-3">
      <input
        type="text"
        placeholder="File name"
        value={fileName}
        onChange={(e) => setFileName(e.target.value)}
        className="p-1"
      />
      <label className="block">
        <span className="sr-only">Choose file</span>
        <input
          type="file"
          onChange={handleFileChange}
          className="block w-full text-sm text-gray-500
                          file:mr-4 file:py-2 file:px-4
                          file:rounded-md file:border-0
                          file:text-sm file:font-semibold
                          file:bg-white file:text-gray-700
                          hover:file:bg-blue-100
                          cursor-pointer"
        />
      </label>
      <button type="submit" className="py-2 px-4 bg-blue-500 text-white font-bold rounded hover:bg-blue-700">
        Upload
      </button>
    </form>
  );
}
