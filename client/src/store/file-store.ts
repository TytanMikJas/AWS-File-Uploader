import { create } from "zustand";
import { devtools } from "zustand/middleware";
import { axiosInstance } from "../utils/axios-config";
import FileDto from "./dto/File.dto";

interface FileStore {
  files: FileDto[];
  uploadFile: (file: File, fileName: string) => Promise<void>;
  fetchFiles: () => Promise<void>;
  deleteFile: (fileId: number) => Promise<void>;
  renameFile: (fileId: number, fileName: string) => Promise<void>;
  downloadFile: (fileId: number) => Promise<void>;
}

export const useFileStore = create<FileStore, [["zustand/devtools", never]]>(
  devtools((set, get) => ({
    files: [],
    uploadFile: async (file, fileName) => {
      const formData = new FormData();
      formData.append("file", file);
      formData.append("fileName", fileName);
      try {
        await axiosInstance.post("/files", formData, {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        });
        get().fetchFiles();
      } catch (error) {
        console.error("Failed to upload file:", error);
      }
    },
    fetchFiles: async () => {
      try {
        const response = await axiosInstance.get("/files");
        set({ files: response.data });
      } catch (error) {
        console.error("Failed to fetch files:", error);
      }
    },
    deleteFile: async (fileId) => {
      try {
        await axiosInstance.delete(`/files/${fileId}`);
        get().fetchFiles();
      } catch (error) {
        console.error("Failed to delete file:", error);
      }
    },
    renameFile: async (fileId, fileName) => {
      try {
        await axiosInstance.patch(`/files/${fileId}/${fileName}`);
        get().fetchFiles();
      } catch (error) {
        console.error("Failed to rename file:", error);
      }
    },
    downloadFile: async (fileId) => {
      try {
        const response = await axiosInstance.get(`/files/${fileId}`, {
          responseType: "blob",
        });
        const url = window.URL.createObjectURL(new Blob([response.data]));
        const link = document.createElement("a");
        link.href = url;
        const file = get().files.find((f) => f.fileId === fileId);
        link.setAttribute("download", file ? file.fileName : "download");
        document.body.appendChild(link);
        link.click();
        link.remove();
      } catch (error) {
        console.error("Failed to download file:", error);
      }
    },
  }))
);
