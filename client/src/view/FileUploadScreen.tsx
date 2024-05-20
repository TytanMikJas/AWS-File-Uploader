import FileColumn from "../components/FileColumn";
import FileInput from "../components/FileInput";

export default function FileUploadScreen() {
  return (
    <div className="flex flex-col items-center space-y-4">
      <h1>File Upload App</h1>
      <FileColumn />
      <FileInput />
    </div>
  );
}
