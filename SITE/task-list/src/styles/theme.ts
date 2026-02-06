export type Theme = typeof theme;

export const theme = {
  colors: {
    primary: "#4F46E5", // Indigo (ações principais)
    primaryHover: "#4338CA",

    secondary: "#22C55E", // Verde (concluir tarefas)
    danger: "#EF4444", // Excluir / erro
    warning: "#F59E0B",

    background: "#F9FAFB", // Fundo geral
    surface: "#FFFFFF", // Cards / containers

    text: {
      primary: "#111827",
      secondary: "#6B7280",
      muted: "#9CA3AF",
      inverse: "#FFFFFF",
    },

    border: "#E5E7EB",

    task: {
      pending: "#F59E0B",
      completed: "#22C55E",
      overdue: "#EF4444",
    },
  },

  typography: {
    fontFamily: "'Inter', system-ui, sans-serif",

    sizes: {
      xs: "0.75rem",
      sm: "0.875rem",
      md: "1rem",
      lg: "1.125rem",
      xl: "1.25rem",
      xxl: "1.5rem",
    },

    weights: {
      regular: 400,
      medium: 500,
      semibold: 600,
      bold: 700,
    },
  },

  spacing: {
    xs: "4px",
    sm: "8px",
    md: "16px",
    lg: "24px",
    xl: "32px",
  },

  radius: {
    sm: "6px",
    md: "10px",
    lg: "16px",
    full: "9999px",
  },

  shadow: {
    sm: "0 1px 2px rgba(0,0,0,0.05)",
    md: "0 4px 8px rgba(0,0,0,0.08)",
    lg: "0 10px 20px rgba(0,0,0,0.12)",
  },
} as const;
