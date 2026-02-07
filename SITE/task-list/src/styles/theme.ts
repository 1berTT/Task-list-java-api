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
      xs: "1.2rem",   /* 12px */
      sm: "1.4rem",   /* 14px */
      md: "1.6rem",   /* 16px */
      lg: "1.8rem",   /* 18px */
      xl: "2rem",     /* 20px */
      xxl: "2.4rem",  /* 24px */
    },

    weights: {
      regular: 400,
      medium: 500,
      semibold: 600,
      bold: 700,
    },
  },

  spacing: {
    xs: "0.4rem",   /* 4px */
    sm: "0.8rem",   /* 8px */
    md: "1.6rem",   /* 16px */
    lg: "2.4rem",   /* 24px */
    xl: "3.2rem",   /* 32px */
  },

  radius: {
    sm: "0.6rem",    /* 6px */
    md: "1rem",      /* 10px */
    lg: "1.6rem",    /* 16px */
    full: "999.9rem", /* 9999px */
  },

  shadow: {
    sm: "0 0.1rem 0.2rem rgba(0,0,0,0.05)",
    md: "0 0.4rem 0.8rem rgba(0,0,0,0.08)",
    lg: "0 1rem 2rem rgba(0,0,0,0.12)",
  },
} as const;
