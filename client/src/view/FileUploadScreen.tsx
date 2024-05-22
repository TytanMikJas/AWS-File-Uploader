import FileColumn from "../components/FileColumn";
import FileInput from "../components/FileInput";
import { useEffect } from "react";
import { useFileStore } from "../store/file-store";

export default function FileUploadScreen() {
  const { files, fetchFiles } = useFileStore();

  useEffect(() => {
    fetchFiles();
  }, [fetchFiles]);

  return (
    <div className="flex flex-col items-center space-y-4">
      <h1>File Upload App</h1>
      <FileColumn files={files} />
      <FileInput />
    </div>
  );
}
