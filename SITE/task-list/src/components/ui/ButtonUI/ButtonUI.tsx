import { Container } from "./styles";
import { ButtonProps } from "./types";

export function ButtonUI({
  children,
  variant = "primary",
  isLoading,
  ...rest
}: ButtonProps) {
  return (
    <Container variant={variant} disabled={isLoading} {...rest}>
      {isLoading ? "Carregando..." : children}
    </Container>
  );
}
