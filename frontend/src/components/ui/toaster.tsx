"use client";

import * as ToastPrimitive from "@radix-ui/react-toast";
import { useToast } from "./use-toast";
import { cn } from "@/lib/utils";

export function Toaster() {
  const { toasts, dismiss } = useToast();
  return (
    <ToastPrimitive.Provider>
      {toasts.map((t) => (
        <ToastPrimitive.Root
          key={t.id}
          open
          onOpenChange={(o) => !o && dismiss(t.id)}
          className={cn(
            "bg-white border rounded p-4 shadow flex justify-between items-start space-x-4",
            t.variant === "destructive" && "bg-red-600 text-white"
          )}
        >
          <div className="grid gap-1">
            <ToastPrimitive.Title className="font-semibold">
              {t.title}
            </ToastPrimitive.Title>
            {t.description && (
              <ToastPrimitive.Description>{t.description}</ToastPrimitive.Description>
            )}
          </div>
          <ToastPrimitive.Close className="text-sm">✕</ToastPrimitive.Close>
        </ToastPrimitive.Root>
      ))}
      <ToastPrimitive.Viewport className="fixed top-0 right-0 p-4 flex flex-col gap-2 w-full max-w-sm z-[100]" />
    </ToastPrimitive.Provider>
  );
}
